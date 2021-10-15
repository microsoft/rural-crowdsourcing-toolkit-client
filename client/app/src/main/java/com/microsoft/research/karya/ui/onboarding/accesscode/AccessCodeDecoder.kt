package com.microsoft.research.karya.ui.onboarding.accesscode

import android.content.Context
import com.microsoft.research.karya.data.exceptions.accesscode.InvalidAccessCodeException
import org.json.JSONObject
import kotlin.properties.Delegates

private const val VERSION_0: Long = 0;
private const val VERSION_1: Long = 1;

private const val PHYSICAL: Int = 0
private const val VIRTUAL: Int = 1

private const val DEV = 0
private const val PROD = 1

private const val DIRECT_MAP = 0
private const val TEMPLATE = 1

private const val DEFAULT_URL: String = "https://www.DEFAULT_URL.com"
private const val PHYSICAL_BOX_URL: String = "http://PRIVATE_IP:PORT"
private const val DEV_URL: String = "http://localhost:8080"

private const val ACCESSCODE_MAPPING_FILENAME = "accesscodeMappings.json"

class AccessCodeDecoder() {
  companion object {
    private var accesscodeLength by Delegates.notNull<Int>()
    private var version by Delegates.notNull<Long>()
    private var boxType: Int? = null
    private var environment: Int? = null
    private var embeddingMechanism: Int? = null
    private lateinit var url: String

    fun decodeURL(context: Context, accesscode: String): String {
      val accesscodeLong = accesscode.toLong()
      version = accesscodeLong and 3
      accesscodeLength = getAccessCodeLength(accesscodeLong, version)
      boxType = getBoxType(accesscodeLong, version)
      environment = getEnvironment(accesscodeLong, version)
      embeddingMechanism = getEmbeddingMechanism(accesscodeLong, version)
      url = getURL(context, accesscodeLong, version, boxType, environment, embeddingMechanism)

      if (accesscode.length != accesscodeLength)
        throw InvalidAccessCodeException("Validation Failed: Wrong access code length")

      return url

    }

    private fun getURL(context: Context, accesscodeLong: Long, version: Long, boxType: Int?, environment: Int?, embeddingMechanism: Int?): String {
      return when(version) {
        VERSION_1 -> {
          if (boxType == PHYSICAL) PHYSICAL_BOX_URL
          if (environment == DEV) DEV_URL

          val JSONmapping = getAccesscodeJSONmapping(context)
          return if (embeddingMechanism == DIRECT_MAP) {
            val mapArray = JSONmapping.getJSONArray("directs")
            val index = (accesscodeLong and 4032).shr(6).toInt()
            // Check if index is available in mappings array
            if (index > mapArray.length())
              throw InvalidAccessCodeException("Index out of range: Index exceeded the length of direct mapping array")
            mapArray.getString(index)
          } else {
            val templatesArray = JSONmapping.getJSONArray("templates")
            val id = (accesscodeLong and 4032).shr(6).toInt()
            val index = (accesscodeLong and 28672).shr(9).toInt()
            val templateString = templatesArray.getString(index)
            // Check if index is available in templates array
            if (index > templatesArray.length())
              throw InvalidAccessCodeException("Index out of range: Index exceeded the length of template mapping array")
            templateString.replace("#".toRegex(), id.toString())
          }
        }
        VERSION_0 -> DEFAULT_URL
        else -> throw InvalidAccessCodeException("Cannot identify the version of access code")
      }
    }

    private fun getAccesscodeJSONmapping(context: Context): JSONObject {
      val fileInString: String =
        context.assets.open(ACCESSCODE_MAPPING_FILENAME).bufferedReader().use { it.readText() }
      return JSONObject(fileInString)
    }

    private fun getEmbeddingMechanism(accesscodeLong: Long, version: Long): Int? {
      if (version == VERSION_1) {
        return (accesscodeLong and 32).shr(5).toInt()
      }

      return null
    }

    private fun getEnvironment(accesscodeLong: Long, version: Long): Int? {
      if (version == VERSION_1) {
        return (accesscodeLong and 16).shr(4).toInt()
      }

      return null
    }

    private fun getBoxType(accesscodeLong: Long, version: Long): Int? {
      if (version == VERSION_1) {
        return (accesscodeLong and 8).shr(3).toInt()
      }
      return null
    }

    private fun getAccessCodeLength(accesscodeLong: Long, version: Long): Int {
      return when(version) {
        VERSION_0 -> (accesscodeLong and 60).shr(2).toInt() + 1
        VERSION_1 -> if ((accesscodeLong and 4).shr(2) == 0L) 8 else 16
        else -> throw Exception("Bad Access Code")
      }
    }
  }

}