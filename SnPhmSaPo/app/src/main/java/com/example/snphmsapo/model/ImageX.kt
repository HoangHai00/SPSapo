package com.example.snphmsapo.model

data class ImageX(
    var created_on: String? = null,
    var file_name: String? = null,
    var full_path: String? = null,
    var id: Int? = null,
    var is_default: Boolean? = null,
    var modified_on: String? = null,
    var path: String? = null,
    var position: Int? = null,
    var size: Double? = null,
) {
    companion object {
        fun getImageX(apiImageX: List<com.example.snphmsapo.api.model.datalistproduct.ImageXData>): List<ImageX>? {
            if (apiImageX.isEmpty()){
                return null
            }
            return apiImageX.map { imageX ->
                ImageX(
                    id = imageX.id,
                    full_path = imageX.full_path
                )
            }
        }
    }
}