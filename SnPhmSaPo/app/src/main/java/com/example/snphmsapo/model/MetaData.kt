package com.example.snphmsapo.model

import com.example.snphmsapo.api.model.datalistproduct.MetadataData

// tạo 1 hàm xét loading
data class MetaData(
    var limit: Int = 0,
    var page: Int = 0,
    var total: Int = 0
) {
    companion object {
        fun getMetaDataProduct(apiMetadata: MetadataData): MetaData {
            return MetaData(
                total = apiMetadata.total,
                page = apiMetadata.page,
                limit = apiMetadata.limit
            )
        }
        fun getMetaDataVariant(apiMetadata: MetadataData): MetaData {
            return MetaData(
                total = apiMetadata.total,
                page = apiMetadata.page,
                limit = apiMetadata.limit

            )
        }

    }

    fun nextPage():Boolean{
        if(total > page * limit){
            return true
        }
        return false
    }


}