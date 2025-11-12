package org.example.app.model

// PUBLIC_INTERFACE
data class Team(
    /** Team display name. */
    val name: String = "",
    /** Team logo URL. */
    val logoUrl: String? = null
)

// PUBLIC_INTERFACE
data class Score(
    /** Home score. */
    val home: Int = 0,
    /** Away score. */
    val away: Int = 0
)

// PUBLIC_INTERFACE
data class Highlight(
    /** Optional highlight title. */
    val title: String? = null,
    /** Public URL to highlight video. */
    val videoUrl: String? = null
)

// PUBLIC_INTERFACE
data class Match(
    /** Unique identifier of the match node. */
    val id: String = "",
    /** Home team. */
    val home: Team = Team(),
    /** Away team. */
    val away: Team = Team(),
    /** Current score. */
    val score: Score = Score(),
    /** Display status like "Q3 08:13" or "FT". */
    val status: String = "",
    /** Optional highlight info. */
    val highlight: Highlight? = null
)
