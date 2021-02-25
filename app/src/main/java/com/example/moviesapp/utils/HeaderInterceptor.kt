package com.example.moviesapp.utils

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        request = request?.newBuilder()
            // dynamic token you get should be use instead of #YOUR_DYNAMIC_TOKEN.
            ?.addHeader("Authorization: Bearer ", "F-LD2qSoIKKc6MC96FZtjTDO_jO_wAQ1I0Xz-OCLukJOdXyVcPcaVw0lfs-t1HBwoc8oa3sli6Dirs6oXVti6HH1Yqo4XWDtw6yZDifbYsMHColr_qjZDJtY6_6rX3Yx")
        ?.build()
        return chain.proceed(request)
    }

    /**
     * Interceptor class for setting of the dynamic headers for every request
     */


}