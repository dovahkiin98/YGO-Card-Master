package net.inferno.ygocardmaster.ui.main

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.inferno.ygocardmaster.ui.card_images.CardImagesPageUI
import net.inferno.ygocardmaster.ui.home.HomePageUI
import net.inferno.ygocardmaster.ui.image.ImagePageUI
import net.inferno.ygocardmaster.ui.search.SearchPageUI
import net.inferno.ygocardmaster.ui.set_cards.SetCardsPageUI
import net.inferno.ygocardmaster.ui.set_list.SetListPageUI
import java.net.URLDecoder

@Composable
fun MainActivityUI() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomePageUI(
                navController = navController,
            )
        }

        composable(Routes.SETS) {
            SetListPageUI(
                navController = navController,
            )
        }

        composable(Routes.SEARCH) {
            SearchPageUI(
                navController = navController,
            )
        }

        composable(
            "${Routes.SET}?name={setName}",
            arguments = listOf(navArgument("setName") { type = NavType.StringType }),
        ) {
            val setName = it.arguments!!.getString("setName")!!

            SetCardsPageUI(
                navController = navController,
                setName = URLDecoder.decode(setName, "UTF-8"),
            )
        }

        composable(
            "${Routes.CARD_IMAGES}?name={cardName}",
            arguments = listOf(navArgument("cardName") { type = NavType.StringType }),
        ) {
            val cardName = it.arguments!!.getString("cardName")!!

            CardImagesPageUI(
                navController = navController,
                cardName = URLDecoder.decode(cardName, "UTF-8"),
            )
        }

        composable(
            "${Routes.IMAGE}?image_url={imageUrl}&title={title}",
            arguments = listOf(
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
            ),
        ) {
            val imageUrl = it.arguments!!.getString("imageUrl")!!
            val title = it.arguments!!.getString("title")!!

            ImagePageUI(
                navController = navController,
                imageUrl = URLDecoder.decode(imageUrl, "UTF-8"),
                title = URLDecoder.decode(title, "UTF-8"),
            )
        }
    }
}

object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val SETS = "sets"
    const val SET = "set"
    const val CARD_IMAGES = "card_images"
    const val IMAGE = "image"

    fun set(setName: String): String {
        return Uri.Builder()
            .path(SET)
            .appendQueryParameter("name", setName)
            .build().toString()
    }

    fun cardImages(cardName: String): String {
        return Uri.Builder()
            .path(CARD_IMAGES)
            .appendQueryParameter("name", cardName)
            .build().toString()
    }

    fun image(imageUrl: String, title: String): String {
        return Uri.Builder()
            .path(IMAGE)
            .appendQueryParameter("image_url", imageUrl)
            .appendQueryParameter("title", title)
            .build().toString()
    }
}