package wittie.test.sundaychill.model

class ListOfMoviesResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieResponse>
)