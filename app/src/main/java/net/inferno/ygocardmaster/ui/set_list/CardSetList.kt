package net.inferno.ygocardmaster.ui.set_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import net.inferno.ygocardmaster.model.CardSet
import java.text.DateFormat

@Composable
fun CardSetList(
    paddingValues: PaddingValues,
    lazyListState: LazyListState,
    cardSets: List<CardSet>,
    onSetClick: (CardSet) -> Unit,
) {
    LazyColumn(
        contentPadding = paddingValues,
        state = lazyListState,
    ) {
        items(cardSets) {
            CardSetItem(it) {
                onSetClick(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSetItem(
    cardSet: CardSet,
    onTap: () -> Unit,
) {
    val dateFormat = remember { DateFormat.getDateInstance() }

    ListItem(
        headlineText = {
            Text("${cardSet.name} (${cardSet.code})")
        },
        supportingText = {
            Text(buildString {
                append("${cardSet.numberOfCards} Cards")
            })
        },
        overlineText = {
            if (cardSet.releaseDate != null) {
                Text("Released On ${dateFormat.format(cardSet.releaseDate)}")
            }
        },
        modifier = Modifier
            .clickable {
                onTap()
            }
    )
}