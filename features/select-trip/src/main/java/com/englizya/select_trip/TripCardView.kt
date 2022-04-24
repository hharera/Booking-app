//package com.englizya.select_trip
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ThumbUp
//import androidx.compose.material.icons.outlined.Share
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.englizya.model.model.Trip
//import com.englyzia.booking.BookingViewModel
//import org.koin.androidx.compose.get
//
//
//@Composable
//fun TripCardView(
//    trip: Trip,
//    bookingViewModel: BookingViewModel = get(),
//    onClick: (Trip) -> Unit
//) {
//    val state = bookingViewModel.state
//    val scope = rememberCoroutineScope()
//
//    when (state) {
//        is PostState.Idle -> {
//
//        }
//
//        is PostState.Error -> {
//
//        }
//
//        is PostState.CommentsFetched -> {
//            postResponse.comments = state.comments
//        }
//
//        is PostState.LikesFetched -> {
//            postResponse.likes = state.likes
//        }
//
//        is PostState.PostFetched -> {
//
//        }
//    }
////card bookingViewModel
//    Card(
//        modifier = Modifier
//            .size(width = 328.dp, height = 203.dp)
//            .padding(vertical = 3.dp)
//            .clickable {
//                onClick(trip)
//            },
//        elevation = 5.dp
//    ) {
//        Box(
//            modifier = Modifier
//                .padding(10.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .align(alignment = Alignment.TopEnd)
//            ) {
//                IconButton(
//                    onClick = {
//                        dropDownState = true
//                    },
//                ) {
//                    Icon(
//                        painterResource(id = R.drawable.menu),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .clickable {
//                            }
//                            .padding(6.dp),
//                        tint = MaterialTheme.colors.secondary,
//                    )
//                }
//
//                DropdownMenu(
//                    expanded = dropDownState,
//                    onDismissRequest = {
//                        dropDownState = false
//                    },
//                    modifier = Modifier.fillMaxWidth(0.3f)
//                ) {
//                    Text(
//                        text = "Delete",
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//            }
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Row {
//                    Image(
//                        painter = rememberImagePainter(
//                            coilLoader.imageRequest(postDetails.user.userImageUrl)
//                        ),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(40.dp)
//                            .clip(CircleShape)
//                            .clickable {
//                                onProfileClicked(postDetails.user.username)
//                            }
//                    )
//
//                    Spacer(modifier = Modifier.size(15.dp))
//
//                    Column {
//                        Text(
//                            //TODO change text value
//                            text = postDetails.user.name,
//                            style = TextStyle(
//                                fontFamily = FontFamily.Default,
//                                fontSize = 16.sp,
//                                color = Color.Black,
//                                fontStyle = FontStyle.Normal,
//                            ),
//                            fontWeight = FontWeight.Bold,
//                            maxLines = 4,
//                            color = MaterialTheme.colors.primary
//                        )
//
//                        Text(
//                            text = timeFromNow(postDetails.post.time),
//                            style = TextStyle(
//                                fontFamily = FontFamily.Serif,
//                                fontSize = timeSize,
//                                fontStyle = FontStyle.Normal,
//                            ),
//                            color = MaterialTheme.colors.primaryVariant
//                        )
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(15.dp))
//
//                Box {
//                    Image(
//                        painter = rememberImagePainter(
//                            coilLoader.imageRequest(postDetails.post.postImageUrl)
//                        ),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .fillMaxHeight(),
//                        alignment = Alignment.Center,
//                        contentScale = ContentScale.Crop,
//                    )
//
//                    val scrollState = rememberScrollState()
//
//                    Text(
//                        text = postDetails.post.caption,
//                        style = TextStyle(
//                            fontFamily = FontFamily.SansSerif,
//                            fontStyle = FontStyle.Normal,
//                        ),
//                        color = MaterialTheme.colors.primary,
//                        modifier = Modifier
//                            .fillMaxWidth(0.6f)
//                            .fillMaxHeight()
//                            .padding(all = 4.dp)
//                            .align(Alignment.Center),
//                        fontSize = 30.sp,
//                    )
//                }
//
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(8.dp)
//                )
//
//                Row(
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            start = 5.dp,
//                            end = 5.dp
//                        ),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                ) {
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(0.5f)
//                            .clickable {
//                                onPostClicked(postDetails.post.postId)
//                            },
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Start,
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.ThumbUp,
//                            contentDescription = null,
//                            modifier = Modifier.size(15.dp)
//                        )
//
//                        Spacer(modifier = Modifier.width(5.dp))
//
//                        Text(
//                            textAlign = TextAlign.Center,
//                            text = "${postDetails.likes.size}",
//                        )
//                    }
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(0.5f)
//                            .clickable {
//
//                            },
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.End
//                    ) {
//                        Text(
//                            text = "${postDetails.comments.size} Comment",
//                            color = MaterialTheme.colors.primary,
//                            maxLines = 1,
//                            fontSize = 12.sp
//                        )
//                    }
//                }
//
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(10.dp)
//                )
//
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(2.dp)
//                        .border(width = 1.dp, color = MaterialTheme.colors.secondary)
//                )
//
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(10.dp)
//                )
//
//                Row(
//                    Modifier
//                        .fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//
//                    LikeIcon(
//                        isLiked = postDetails.likes.any { it.username == username },
//                        onClicked = { onLikeClicked(postDetails.post.postId) }
//                    )
//
//                    CommentIcon(
//                        onClicked = {
//                            commentFieldState = true
//                        }
//                    )
//
//                    Row(
//                        modifier = Modifier
//                            .clickable {
//
//                            },
//                        horizontalArrangement = Arrangement.Center,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            imageVector = Icons.Outlined.Share,
//                            contentDescription = null,
//                            modifier = Modifier.size(25.dp)
//                        )
//                        Spacer(modifier = Modifier.size(5.dp))
//
//                        Text(
//                            text = share,
//                            textAlign = TextAlign.Center,
//                        )
//                    }
//                }
//
//                if (commentFieldState)
//                    OutlinedTextField(
//                        value = comment,
//                        onValueChange = {
//                            comment = it
//                        },
//                        placeholder = {
//                            Text(text = "Comment")
//                        },
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .padding(top = 12.dp)
//                            .fillMaxWidth(),
//                        keyboardOptions = KeyboardOptions(
//                            autoCorrect = true,
//                            imeAction = ImeAction.Done,
//                            keyboardType = KeyboardType.Text
//                        ),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                        ),
//                        keyboardActions = KeyboardActions(
//                            onDone = {
//                                commentFieldState = false
//                                whenCommentSubmitted(
//                                    comment,
//                                    postDetails.post.postId
//                                )
//                            }
//                        )
//                    )
//            }
//        }
//    }
//}
//
////preview for
//@Preview(showBackground = true)
//@Composable()
//fun previewTripCardView(): Unit {
//    TripCardView(
//        Trip()
//    ) {
//
//    }
//
//}
