package com.eduardodenis.loteria.ui.theme.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
