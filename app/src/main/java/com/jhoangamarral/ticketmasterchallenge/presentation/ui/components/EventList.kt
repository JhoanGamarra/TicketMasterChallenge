package com.jhoangamarral.ticketmasterchallenge.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.jhoangamarral.ticketmasterchallenge.R
import com.jhoangamarral.ticketmasterchallenge.presentation.entities.EventListItem
import com.jhoangamarral.ticketmasterchallenge.presentation.util.PreviewContainer

@Composable
fun EventList(
    events: LazyPagingItems<EventListItem>,
) {
    val imageSize = ImageSize.getImageFixedSize()
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(events.itemCount) { index ->
            when (val event = events[index]) {
                is EventListItem -> EventItem(event, imageSize)
                else -> Loader()
            }
        }
    }
}

@Composable
private fun EventItem(
    event: EventListItem,
    imageSize: ImageSize,
) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .weight(1f)
                    .padding(top = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = event.imageUrl,
                    loading = { EventItemPlaceholder() },
                    error = { EventItemPlaceholder() },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(3.dp)
                        .size(imageSize.width, imageSize.height)
                        .clip(RoundedCornerShape(2))
                )
            }
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = event.title,
                    softWrap = true,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Visible,
                    maxLines = 4
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = event.startDate ?: "empty startDate", color = Color.Gray)
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = event.venue ?: "empty venue", color = Color.Gray)
            }
        }
    }

}

@Composable
fun EventItemPlaceholder() {
    Image(
        painter = painterResource(id = R.drawable.bg_image),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}

private class ImageSize(
    val width: Dp,
    val height: Dp
) {
    companion object {
        @Composable
        fun getImageFixedSize(): ImageSize {
            val configuration = LocalConfiguration.current
            val imageWidth = configuration.screenWidthDp.dp / 3
            val imageHeight = imageWidth.times(1.3f)
            return ImageSize(imageWidth, imageHeight)
        }
    }
}

@Preview("Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SeparatorAndEventItem() {
    PreviewContainer {
        Surface {
            val imageSize = ImageSize.getImageFixedSize()
            Column {
                Row {
                    EventItem(
                        EventListItem(
                            "1",
                            "Title 1",
                            "https://i.stack.imgur.com/lDFzt.jpg",
                            "Los Angeles",
                            "09/10/24"
                        ), imageSize
                    )
                    EventItem(
                        EventListItem(
                            "1",
                            "Title 1",
                            "https://i.stack.imgur.com/lDFzt.jpg",
                            "Los Angeles",
                            "09/10/24"
                        ), imageSize
                    )
                    EventItem(
                        EventListItem(
                            "1",
                            "Title 1",
                            "https://i.stack.imgur.com/lDFzt.jpg",
                            "Los Angeles",
                            "09/10/24"
                        ), imageSize
                    )
                    EventItem(
                        EventListItem(
                            "1",
                            "Title 1",
                            "https://i.stack.imgur.com/lDFzt.jpg",
                            "Los Angeles",
                            "09/10/24"
                        ), imageSize
                    )
                }
            }
        }
    }
}
