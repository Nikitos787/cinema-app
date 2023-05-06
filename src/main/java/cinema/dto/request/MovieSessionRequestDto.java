package cinema.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class MovieSessionRequestDto {
    @Positive
    @NotNull
    private Long movieId;
    @Positive
    @NotNull
    private Long cinemaHallId;
    @FutureOrPresent
    @NotNull
    private LocalDateTime showTime;

    public Long getMovieId() {
        return movieId;
    }

    public Long getCinemaHallId() {
        return cinemaHallId;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }
}
