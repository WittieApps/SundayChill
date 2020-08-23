package wittie.test.sundaychill.domain

import wittie.test.sundaychill.model.MovieRepresentation
import wittie.test.sundaychill.model.MovieResponse

fun MovieResponse.toMovieRepresentation() = MovieRepresentation(
    id = this.id,
    title = this.title,
    overview = this.overview,
    vote_average = this.vote_average,
    poster_path = this.poster_path
)