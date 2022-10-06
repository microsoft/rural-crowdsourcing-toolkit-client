package com.microsoft.research.karya.ui.scenarios.imageLabelling

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.microsoft.research.karya.R
import com.microsoft.research.karya.databinding.ItemFloatLabelBinding
import com.microsoft.research.karya.databinding.MicrotaskImageLabellingBinding
import com.microsoft.research.karya.ui.scenarios.common.BaseMTRendererFragment
import com.microsoft.research.karya.utils.extensions.observe
import com.microsoft.research.karya.utils.extensions.viewBinding
import com.microsoft.research.karya.utils.extensions.viewLifecycleScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageLabellingFragment : BaseMTRendererFragment(R.layout.microtask_image_labelling) {
    override val viewModel: ImageLabellingViewModel by viewModels()
    private val args: ImageLabellingFragmentArgs by navArgs()

    private val binding by viewBinding(MicrotaskImageLabellingBinding::bind)

    private val labelMap: MutableMap<String, View> = mutableMapOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        viewModel.setupViewModel(args.taskId, args.completed, args.total)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()

        // Set microtask instruction
        val instruction =
            try {
                viewModel.task.params.asJsonObject.get("instruction").asString
            } catch (e: Exception) {
                getString(R.string.image_labelling_instruction)
            }
        with(binding) {
            instructionTv.text = instruction

            // Set next button click handler
            nextBtn.setOnClickListener { handleNextClick() }

            // Set up label views
            val labels =
                try {
                    viewModel.task.params.asJsonObject.get("labels").asJsonArray.map { it.asString }
                } catch (e: Exception) {
                    arrayListOf()
                }

            labels.forEach { value ->
                ItemFloatLabelBinding.inflate(layoutInflater).apply {
                    label.text = value

                    root.setOnClickListener { viewModel.flipState(value) }
                    labelsLayout.addView(root)

                    labelMap[value] = root
                }
            }
        }
    }

    private fun handleNextClick() {
        viewModel.completeLabelling()
    }

    private fun setupObservers() {
        viewModel.imageFilePath.observe(viewLifecycleOwner.lifecycle, viewLifecycleScope) { path ->
            if (path.isNotEmpty()) {
                val image: Bitmap = BitmapFactory.decodeFile(path)
                binding.sourceImageIv.setImageBitmap(image)
            } else {
                binding.sourceImageIv.setImageDrawable(null)
            }
        }

        viewModel.labelState.observe(viewLifecycleOwner.lifecycle, viewLifecycleScope) { labelState ->
            Log.d("debug-label-state", labelState.toString())
            labelMap.forEach { (s, view) ->
                val state = labelState[s] ?: false
                val color = if (state) R.color.c_light_green else R.color.c_light_grey
                view.findViewById<CardView>(R.id.label_card).background.setTint(ContextCompat.getColor(requireContext(), color))
            }
        }
    }
}
