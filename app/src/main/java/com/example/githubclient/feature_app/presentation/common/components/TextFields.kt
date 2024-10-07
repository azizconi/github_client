package com.example.githubclient.feature_app.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.SearchTextField(
    value: TextFieldValue,
    onValueChanged: (TextFieldValue) -> Unit
) {

    Box(
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 8.dp)
            .background(Color(0xFFF1F1F1), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon for Search
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = value,
                onValueChange = onValueChanged,
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp
                ),
                maxLines = 1,
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (value.text.isEmpty()) {
                            Text(
                                text = "Поиск репозиториев...",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }


}