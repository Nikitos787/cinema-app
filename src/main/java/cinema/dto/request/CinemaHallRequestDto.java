package cinema.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CinemaHallRequestDto {
    @Positive
    @Min(10)
    private int capacity;
    @NotEmpty
    @Size(max = 200)
    private String description;

    public int getCapacity() {
        return capacity;
    }

    public String getDescription() {
        return description;
    }
}
