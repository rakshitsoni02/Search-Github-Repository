package com.rax.ui.components.image

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.size.pxOrElse
import com.rax.shared.ui.components.R
import okhttp3.HttpUrl.Companion.toHttpUrl

/**
 * A wrapper around [AsyncImage], setting a default [contentScale] and showing
 * content while loading.
 */
@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        placeholder = ColorPainter(placeholderColor),
        error = painterResource(id = R.drawable.ic_error_48x48),
        modifier = modifier,
        contentScale = contentScale
    )
}

/**
 * A Coil [Interceptor] which appends query params to Unsplash urls to request sized images.
 */
object UnsplashSizingInterceptor : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val data = chain.request.data
        val widthPx = chain.size.width.pxOrElse { -1 }
        val heightPx = chain.size.height.pxOrElse { -1 }
        if (widthPx > 0 && heightPx > 0 && data is String &&
            data.startsWith("https://avatars.githubusercontent.com/")
        ) {
            val url = data.toHttpUrl()
                .newBuilder()
                .addQueryParameter("w", widthPx.toString())
                .addQueryParameter("h", heightPx.toString())
                .build()
            val request = chain.request.newBuilder().data(url).build()
            return chain.proceed(request)
        }
        return chain.proceed(chain.request)
    }
}
