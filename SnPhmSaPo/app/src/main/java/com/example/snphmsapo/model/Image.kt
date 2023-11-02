package com.example.snphmsapo.model


data class Image(
    var created_on: String? = null,
    var file_name: String? = null,
    var full_path: String? = null,
    var id: Int? = null,
    var modified_on: String? = null,
    var path: String? = null,
    var position: Int? = null,
    var size: Double? = null,
) {
    companion object {
        fun getImage(apiImageData: List<com.example.snphmsapo.api.model.datalistproduct.ImageData>): List<Image> {
            return apiImageData.map { apiImage ->
                Image(
                    id = apiImage.id,
                    full_path = apiImage.full_path
                )
            }
        }
    }


}