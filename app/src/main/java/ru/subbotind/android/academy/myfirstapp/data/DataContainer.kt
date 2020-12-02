package ru.subbotind.android.academy.myfirstapp.data

import ru.subbotind.android.academy.myfirstapp.R

object DataContainer {

    //avengers cast
    private val downewJR = Actor(1, "Robert Downey Jr.", R.drawable.robert_d_j)
    private val evansC = Actor(2, "Chris Evans", R.drawable.evanc_c)
    private val rufaloM = Actor(3, "Mark Ruffalo", R.drawable.rufalo_m)
    private val hemsworthC = Actor(4, "Chris Hemsworth", R.drawable.hemsworth_c)

    //wonder woman cast
    private val pascalP = Actor(5, "Pedro Pascal")
    private val gadotG = Actor(6, "Gal Gadot")
    private val nielsenC = Actor(7, "Connie Nielsen")

    //black widow cast
    private val johanssonS = Actor(8, "Scarlett Johansson")
    private val pughF = Actor(9, "Florence Pugh")
    private val weiszR = Actor(10, "Rachel Weisz")

    //tenet cast
    private val washingtonJD = Actor(11, "John David Washington")
    private val pattinsonR = Actor(12, "Robert Pattinson")
    private val debickiE = Actor(13, "Elizabeth Debicki")

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
            duration = 97,
            pgRating = "16+",
            userRating = 5,
            reviewsCount = 98,
            isLiked = true,
            storyLine = "Armed with only one word, Tenet, and fighting for the survival of the entire world, a Protagonist journeys through a twilight world of international espionage on a mission that will unfold in something beyond real time.",
            cast = listOf(washingtonJD, pattinsonR, debickiE),
            tags = "Action, Sci-Fi, Thriller",
            promoImage = R.drawable.tenet_promo_image,
            mainImageBackground = R.drawable.avengers_background
        ),
        3L to Movie(
            id = 3,
            title = "Black Widow",
            duration = 102,
            pgRating = "13+",
            userRating = 4,
            reviewsCount = 38,
            isLiked = false,
            storyLine = "A film about Natasha Romanoff in her quests between the films Civil War and Infinity War.",
            cast = listOf(johanssonS, downewJR, pughF, weiszR),
            tags = "Action, Adventure, Sci-Fi",
            promoImage = R.drawable.black_widow_promo,
            mainImageBackground = R.drawable.avengers_background
        ),
        4L to Movie(
            id = 4,
            title = "Wonder Woman 1984",
            duration = 120,
            pgRating = "13+",
            userRating = 5,
            reviewsCount = 74,
            isLiked = false,
            storyLine = "Fast forward to the 1980s as Wonder Woman's next big screen adventure finds her facing two all-new foes: Max Lord and The Cheetah.",
            cast = listOf(),
            tags = "Action, Adventure, Fantasy",
            promoImage = R.drawable.wonder_woman_promo,
            mainImageBackground = R.drawable.avengers_background
        )
    )

    fun getMovie(id: Long): Movie = moviesMap[id] ?: error("Cannot find such movie ID=$id")

    fun getAllMovies(): List<Movie> {
        return moviesMap.map { it.value }
    }
}