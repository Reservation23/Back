package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.dao.Member;
import zerobase.reservation.dao.Store;
import zerobase.reservation.dto.StoreDto;
import zerobase.reservation.repository.MemberRepository;
import zerobase.reservation.repository.StoreRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static zerobase.reservation.dto.StoreDto.toStoreEntity;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    public StoreDto join(StoreDto storeDto) {
        Member member = memberRepository.findById(storeDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + storeDto.getMemberId()));

        Store savedStore = storeRepository.save(toStoreEntity(member, storeDto));
        return storeToDto(savedStore);
    }

    @Transactional(readOnly = true)
    public StoreDto findById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No Store found with id: " + id));
        return storeToDto(store);
    }

    @Transactional(readOnly = true)
    public List<StoreDto> findAll() {
        return storeRepository.findAll()
                .stream()
                .map(this::storeToDto)
                .collect(Collectors.toList());
    }

    public StoreDto updateStore(StoreDto storeDto, Long id) {
        Store store = storeRepository.findById(id).get();

        updateStore(storeDto, store);

        return storeToDto(store);
    }

    private void updateStore(StoreDto storeDto, Store store) {
        store.setName(storeDto.getName());
        store.setLocation(storeDto.getLocation());
        store.setDescription(storeDto.getDescription());
        store.setUpdatedAt(LocalDateTime.now());
    }

    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }

    private StoreDto storeToDto(Store store) {
        return new StoreDto().builder()
                .storeId(store.getId())
                .memberId(store.getMember().getId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .build();
    }
}
