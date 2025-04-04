import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.PointDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.dto.TrainDto;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.entities.Train;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.exceptions.ResourceNotFoundException;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.repositories.TrainRepository;
import com.backend.railwaycommodityreservationsystem.RailwayCommodityReservationSystem.services.impl.TrainServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TrainServiceImplTest {

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TrainServiceImpl trainService;

    private Train train;
    private TrainDto trainDto;

    @BeforeEach
    void setUp() {
        train = new Train();
        train.setId(1L);
        train.setRating(4.5);
        train.setAvailable(true);
        train.setTrainId("T123");
        train.setCurrentLocation(null); // Adjust this if needed

        trainDto = new TrainDto(1L, 4.5, true, "T123", new PointDto(new double[]{1.0, 2.0}));
    }

    @Test
    void testGetCurrentTrain_WhenTrainExists() {
        when(trainRepository.findById(2L)).thenReturn(Optional.of(train));
        Train result = trainService.getCurrentTrain();
        assertNotNull(result);
        assertEquals(train.getId(), result.getId());
    }

    @Test
    void testGetCurrentTrain_WhenTrainNotFound() {
        when(trainRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> trainService.getCurrentTrain());
    }

    @Test
    void testUpdateTrainAvailability() {
        when(trainRepository.save(any(Train.class))).thenReturn(train);
        Train result = trainService.updateTrainAvailability(train, false);
        assertNotNull(result);
        assertEquals(false, result.getAvailable()); // Use getAvailable() instead of isAvailable()
    }

    @Test
    void testUpdateLocation_WhenTrainExists() {
        when(trainRepository.findByTrainId("T123")).thenReturn(Optional.of(train));
        when(modelMapper.map(trainDto, Train.class)).thenReturn(train);
        when(trainRepository.save(any(Train.class))).thenReturn(train);
        when(modelMapper.map(train, TrainDto.class)).thenReturn(trainDto);

        TrainDto result = trainService.updateLocation(trainDto, "T123");
        assertNotNull(result);
        assertEquals(trainDto.getTrainId(), result.getTrainId());
        assertEquals(trainDto.getAvailable(), result.getAvailable()); // Updated to use getAvailable()
    }

    @Test
    void testUpdateLocation_WhenTrainNotFound() {
        when(trainRepository.findByTrainId("T123")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> trainService.updateLocation(trainDto, "T123"));
    }
}
