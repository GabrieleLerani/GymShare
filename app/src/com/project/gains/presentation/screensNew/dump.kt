import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.decode.GifDecoder
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.project.gains.R

@Composable
fun p() {
    val context = LocalContext.current


    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(GifDecoder.Factory()) // Use built-in decoder for Android P+
        }
        .build()

    MyComposable(gif = R.drawable.g, imageLoader = imageLoader)
}

@Composable
fun MyComposable(gif: Int, imageLoader: ImageLoader) {
    val painter = // Adjust the size as needed
        rememberAsyncImagePainter(ImageRequest // Optional: Apply transformations
            .Builder(LocalContext.current).data(data = gif).apply(block = fun ImageRequest.Builder.() {
                size(Size.ORIGINAL) // Adjust the size as needed
                transformations(CircleCropTransformation()) // Optional: Apply transformations
            }).build(), imageLoader = imageLoader
        )

    Image(
        painter = painter,
        contentDescription = null, // Provide content description if necessary
        modifier = Modifier.size(200.dp) // Example: Set image size
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewP() {
    p()
}
