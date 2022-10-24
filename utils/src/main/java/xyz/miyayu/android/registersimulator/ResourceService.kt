package xyz.miyayu.android.registersimulator

import android.content.Context
import android.content.res.Resources
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ResourceService {
    fun getResources(): Resources

    companion object {
        class ResourceServiceImpl @Inject constructor(
            @ApplicationContext val context: Context
        ) : ResourceService {
            override fun getResources(): Resources {
                return context.resources
            }
        }
    }
}
