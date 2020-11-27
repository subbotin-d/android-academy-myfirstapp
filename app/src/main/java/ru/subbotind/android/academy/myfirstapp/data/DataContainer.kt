package ru.subbotind.android.academy.myfirstapp.data

import ru.subbotind.android.academy.myfirstapp.R

object DataContainer {

    private val downewJR = Actor("Robert Downey Jr.", R.drawable.robert_d_j)
    private val evansC = Actor("Chris Evans", R.drawable.evanc_c)
    private val rufaloM = Actor("Mark Ruffalo", R.drawable.rufalo_m)
    private val hemsworthC = Actor("Chris Hemsworth", R.drawable.hemsworth_c)

    private val moviesMap = mapOf(
        1L to Movie(
            id = 1,
            title = "Avengers: End Game",
            duration = 135,
            pgRating = "13+",
            userRating = 4,
            reviewsCount = 125,
            isLiked = false,
            storyLine = "After the devastating events of Avengers: Infinity War, the\n" +
                    "universe is in ruins. With the help of remaining allies, the Avengers assemble " +
                    "once more in order to reverse Thanos\' actions and restore balance to the universe.",
            cast = listOf(downewJR, evansC, rufaloM, hemsworthC),
            tags = "Action, Adventure, Fantasy",
            promoImage = R.drawable.avengers_promo_image,
            mainImageBackground = R.drawable.avengers_background
        ),
        2L to Movie(
            id = 2,
            title = "Tenet",
            duration = 100,
            pgRating = "16+",
            userRating = 5,
            reviewsCount = 165,
            isLiked = true,
            storyLine = "After the devastating events of Avengers: Infinity War, the\n" +
                    "universe is in ruins. With the help of remaining allies, the Avengers assemble " +
                    "once more in order to reverse Thanos\' actions and restore balance to the universe.",
            cast = listOf(rufaloM, hemsworthC, downewJR, evansC),
            tags = "Action, Adventure",
            promoImage = R.drawable.avengers_promo_image,
            mainImageBackground = R.drawable.avengers_background
        ),
        3L to Movie(
            id = 3,
            title = "Avengers: End Game",
            duration = 135,
            pgRating = "13+",
            userRating = 4,
            reviewsCount = 125,
            isLiked = true,
            storyLine = "After the devastating events of Avengers: Infinity War, the\n" +
                    "universe is in ruins. With the help of remaining allies, the Avengers assemble " +
                    "once more in order to reverse Thanos\' actions and restore balance to the universe.",
            cast = listOf(downewJR, evansC, rufaloM, hemsworthC),
            tags = "Action, Adventure, Fantasy",
            promoImage = R.drawable.avengers_promo_image,
            mainImageBackground = R.drawable.avengers_background
        ),
        4L to Movie(
            id = 4,
            title = "Avengers: End Game",
            duration = 135,
            pgRating = "13+",
            userRating = 4,
            reviewsCount = 125,
            isLiked = true,
            storyLine = "After the devastating events of Avengers: Infinity War, the\n" +
                    "universe is in ruins. With the help of remaining allies, the Avengers assemble " +
                    "once more in order to reverse Thanos\' actions and restore balance to the universe.",
            cast = listOf(downewJR, evansC, rufaloM, hemsworthC),
            tags = "Action, Adventure, Fantasy",
            promoImage = R.drawable.avengers_promo_image,
            mainImageBackground = R.drawable.avengers_background
        ),
        5L to Movie(
            id = 5,
            title = "Avengers: End Game",
            duration = 135,
            pgRating = "13+",
            userRating = 4,
            reviewsCount = 125,
            isLiked = true,
            storyLine = "After the devastating events of Avengers: Infinity War, the\n" +
                    "universe is in ruins. With the help of remaining allies, the Avengers assemble " +
                    "once more in order to reverse Thanos\' actions and restore balance to the universe.",
            cast = listOf(downewJR, evansC, rufaloM, hemsworthC),
            tags = "Action, Adventure, Fantasy",
            promoImage = R.drawable.avengers_promo_image,
            mainImageBackground = R.drawable.avengers_background
        ),
        6L to Movie(
            id = 6,
            title = "Avengers: End Game",
            duration = 135,
            pgRating = "13+",
            userRating = 4,
            reviewsCount = 125,
            isLiked = true,
            storyLine = "After the devastating events of Avengers: Infinity War, the\n" +
                    "universe is in ruins. With the help of remaining allies, the Avengers assemble " +
                    "once more in order to reverse Thanos\' actions and restore balance to the universe.",
            cast = listOf(downewJR, evansC, rufaloM, hemsworthC),
            tags = "Action, Adventure, Fantasy",
            promoImage = R.drawable.avengers_promo_image,
            mainImageBackground = R.drawable.avengers_background
        ),
        7L to Movie(
            id = 7,
            title = "Avengers: End Game",
            duration = 135,
            pgRating = "13+",
            userRating = 4,
            reviewsCount = 125,
            isLiked = true,
            storyLine = "After the devastating events of Avengers: Infinity War, the\n" +
                    "universe is in ruins. With the help of remaining allies, the Avengers assemble " +
                    "once more in order to reverse Thanos\' actions and restore balance to the universe.",
            cast = listOf(downewJR, evansC, rufaloM, hemsworthC),
            tags = "Action, Adventure, Fantasy",
            promoImage = R.drawable.avengers_promo_image,
            mainImageBackground = R.drawable.avengers_background
        ),
        8L to Movie(
            id = 8,
            title = "Avengers: End Game",
            duration = 135,
            pgRating = "13+",
            userRating = 4,
            reviewsCount = 125,
            isLiked = true,
            storyLine = "After the devastating events of Avengers: Infinity War, the\n" +
                    "universe is in ruins. With the help of remaining allies, the Avengers assemble " +
                    "once more in order to reverse Thanos\' actions and restore balance to the universe.",
            cast = listOf(downewJR, evansC, rufaloM, hemsworthC),
            tags = "Action, Adventure, Fantasy",
            promoImage = R.drawable.avengers_promo_image,
            mainImageBackground = R.drawable.avengers_background
        )
    )

    fun getMovie(id: Long): Movie = moviesMap[id] ?: error("Cannot find such movie ID=$id")

    fun getAllMovies(): List<Movie> {
        return moviesMap.map { it.value }
    }
}