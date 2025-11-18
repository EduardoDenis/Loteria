package com.eduardodenis.loteria.ui.theme.component

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eduardodenis.loteria.R
import com.eduardodenis.loteria.ui.theme.Green
import com.eduardodenis.loteria.ui.theme.LoteriaTheme

@Composable
fun LoItemType(
    name: String,
    color: Color = Color.Black,
    bgColor: Color = Color.Transparent,
    @DrawableRes icon: Int = R.drawable.logo_mega_sena
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .wrapContentSize()
            .background(bgColor)
    ) {
        Image(
            painterResource(icon),
            contentDescription = stringResource(id = R.string.trevo),
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp)
        )

        Text(
            text = name,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(top = 4.dp))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoTextDropDown(
    label: String,
    initial: String,
    list: List<String>,
    onItemChanged: (String) -> Unit
) {
    var dropDownExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(initial) }

    ExposedDropdownMenuBox(
        expanded = dropDownExpanded,
        onExpandedChange = {
            dropDownExpanded = !dropDownExpanded
        }) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = selectedText,
            readOnly = true,
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropDownExpanded)
            },
            onValueChange = {}
        )
        ExposedDropdownMenu(
            expanded = dropDownExpanded,
            onDismissRequest = {
                Log.i("recriando", "recriando a tela")
                dropDownExpanded = false
            }) {
            Log.i("apareceu", "apareceu as opcoes")
            list.forEach { item ->
                DropdownMenuItem(text = { Text(item) }, onClick = {
                    selectedText = item
                    onItemChanged(item)
                    dropDownExpanded = false
                })
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AutoTextDropDownPreview() {
    AutoTextDropDown(label = "", initial = "", listOf(), {})

}

@Composable
fun LoNumberTextField(
    value: String,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    imeAction: ImeAction = ImeAction.Next,
    onValueChanged: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        maxLines = 1,
        label = {
            Text(stringResource(id = label))
        },
        placeholder = {
            Text(stringResource(id = placeholder))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        onValueChange = onValueChanged

    )

}

@Preview(showBackground = true)
@Composable
fun LoItemTypePreview() {
    LoItemType(
        name = "Mega Sena",
        color = Color.White,
        bgColor = Green,
        icon = R.drawable.logo_mega_sena
    )
}


@Preview(showBackground = true)
@Composable
fun LoNumberTextFieldPreview() {
    LoteriaTheme {
        LoNumberTextField(
            value = "",
            label = R.string.trevo,
            placeholder = R.string.mega_rule,
            imeAction = ImeAction.Done
        ) {

        }
    }
}
