// package org.depromeet.spot.usecase.service.fake;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.stream.Collectors;
//
// import org.depromeet.spot.domain.review.image.ReviewImage;
// import org.depromeet.spot.usecase.port.out.review.ReviewImageRepository;
//
// public class FakeReviewImageRepository implements ReviewImageRepository {
//    private final List<ReviewImage> data = new ArrayList<>();
//
//    @Override
//    public List<ReviewImage> findTopReviewImagesByStadiumIdAndBlockCode(
//            Long stadiumId, String blockCode, int limit) {
//        return data.stream()
//                .filter(
//                        image ->
//                                image.getReview().getStadium().getId().equals(stadiumId)
//                                        &&
// image.getReview().getBlock().getCode().equals(blockCode))
//                .limit(limit)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ReviewImage> saveAll(List<ReviewImage> reviewImages) {
//        data.addAll(reviewImages);
//        return reviewImages;
//    }
//
//    public void clear() {
//        data.clear();
//    }
// }
