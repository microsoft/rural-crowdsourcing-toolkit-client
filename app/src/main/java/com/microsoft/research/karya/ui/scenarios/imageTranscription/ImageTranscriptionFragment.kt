package com.microsoft.research.karya.ui.scenarios.imageTranscription

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.microsoft.research.karya.R
import com.microsoft.research.karya.databinding.MicrotaskImageTranscriptionBinding
import com.microsoft.research.karya.ui.scenarios.common.BaseMTRendererFragment
import com.microsoft.research.karya.utils.extensions.observe
import com.microsoft.research.karya.utils.extensions.requestSoftKeyFocus
import com.microsoft.research.karya.utils.extensions.viewBinding
import com.microsoft.research.karya.utils.extensions.viewLifecycleScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageTranscriptionFragment : BaseMTRendererFragment(R.layout.microtask_image_transcription) {
    override val viewModel: ImageTranscriptionViewModel by viewModels()
    val args: ImageTranscriptionFragmentArgs by navArgs()

    private val binding by viewBinding(MicrotaskImageTranscriptionBinding::bind)

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
                getString(R.string.image_transcription_instruction)
            }
        with(binding) {
            instructionTv.text = instruction

            // Set next button click handler
            nextBtn.setOnClickListener { handleNextClick() }

            // Get keyboard focus
            requestSoftKeyFocus(transcriptionEv)
        }
    }

    private fun handleNextClick() = with(binding) {
        val transcription = transcriptionEv.text.toString()
        viewModel.completeTranscription(transcription)
        transcriptionEv.setText("")
        requestSoftKeyFocus(transcriptionEv)
    }

    private fun setupObservers() = with(binding) {
        viewModel.imageFilePath.observe(viewLifecycleOwner.lifecycle, viewLifecycleScope) { path ->
            if (path.isNotEmpty()) {
                val image: Bitmap = BitmapFactory.decodeFile(path)
                sourceWordIv.setImageBitmap(image)
            } else {
                sourceWordIv.setImageResource(0)
            }
        }
    }
}
