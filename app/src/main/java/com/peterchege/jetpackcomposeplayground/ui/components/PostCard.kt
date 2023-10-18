
package com.peterchege.jetpackcomposeplayground.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.peterchege.jetpackcomposeplayground.api.responses.Post

@Composable
fun PostCard(
    post: Post,
    onPostClick: (Post) -> Unit,

    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(15.dp), clip = true)
            .clickable { onPostClick(post) },

        shape = RoundedCornerShape(15.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f)

                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = post.title,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        )
                }
            }
        }
    }
}