//package com.example.remup.ui.show_note_screen
//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Card
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontStyle
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavController
//import com.example.remup.data.model.Note
//import com.example.remup.data.view_model.NoteViewModel
//import java.time.format.DateTimeFormatter
//import java.time.format.FormatStyle
//
//
//@Composable
//fun ShowNoteScreen(
//    navController: NavController,
//    noteViewModel: NoteViewModel = hiltViewModel()
//) {
//    val noteList = noteViewModel.noteList().collectAsState(initial = emptyList()).value
//    var randomNote by remember { mutableStateOf(Note(0,"")) }
//    LaunchedEffect(noteList.any())
//    {
//        if(noteList.any())
//            randomNote = noteList.random()
//    }
//
//
//    Column(
//        modifier = Modifier
//            .background(MaterialTheme.colors.background)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Card(
//            modifier = Modifier
//                .padding(vertical = 70.dp, horizontal = 30.dp)
//                .fillMaxWidth()
//                .wrapContentHeight(),
//            shape = RoundedCornerShape(21.dp),
//            elevation = 2.dp,
//            backgroundColor = MaterialTheme.colors.surface,
//
//        ) {
//            Column(
//                verticalArrangement = Arrangement.SpaceBetween
//            ) {
//
//
//                Text(
//                    modifier = Modifier
//                        .padding(30.dp)
//                        .wrapContentHeight(Alignment.Top)
//                        .fillMaxWidth(),
//
//                    text = if (noteList.any()) randomNote.data else "NO NOTE EXIST",
//                    color = MaterialTheme.colors.primary,
//                    fontSize = 18.sp,
//                    fontFamily = FontFamily.Serif,
//                    textAlign = TextAlign.Justify,
//                    lineHeight = 32.sp,
//                )
//
//                Text(
//                    modifier = Modifier
//                        .padding(bottom = 30.dp, start = 30.dp, end = 30.dp)
//                        .wrapContentHeight(Alignment.Bottom)
//                        .fillMaxWidth(),
//                    text = if (noteList.any()) randomNote.created.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)) else "",
//                    color = MaterialTheme.colors.primary,
//                    fontSize = 14.sp,
//                    fontFamily = FontFamily.Serif,
//                    textAlign = TextAlign.End,
//                    fontStyle = FontStyle.Italic
//                )
//            }
//        }
//    }
//}