package net.molleafauss.loomtest.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.molleafauss.loomtest.controllers.models.SampleData;
import net.molleafauss.loomtest.data.models.User;
import net.molleafauss.loomtest.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final Random rnd = new Random();

    private final AtomicInteger iteration = new AtomicInteger(0);

    private final LoadingCache<String, List<SampleData>> data = Caffeine.newBuilder()
            .refreshAfterWrite(1, TimeUnit.MINUTES)
            .build(key -> createData());

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        data.get("value");
    }

    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    public SampleData getSampleData() {
        val cache = data.get("value");
        return cache.get(rnd.nextInt(cache.size()));
    }

    private List<SampleData> createData() throws InterruptedException {
        long t0 = System.currentTimeMillis();
        int it = iteration.incrementAndGet();
        var items = rnd.nextInt(50);
        val data = new ArrayList<SampleData>();
        for (int i = 0; i < items; i++) {
            data.add(new SampleData("ABC", it));
            Thread.sleep(rnd.nextInt(500) + 100);
        }
        log.info("Created cache - {} items, {}ms", items, System.currentTimeMillis() - t0);
        return data;
    }
}
