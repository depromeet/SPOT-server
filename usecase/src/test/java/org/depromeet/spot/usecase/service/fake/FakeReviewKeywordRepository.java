// package org.depromeet.spot.usecase.service.fake;
//
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;
//
// import org.depromeet.spot.domain.review.keyword.ReviewKeyword;
// import org.depromeet.spot.domain.review.result.KeywordCount;
// import org.depromeet.spot.usecase.port.out.review.ReviewKeywordRepository;
//
// public class FakeReviewKeywordRepository implements ReviewKeywordRepository {
//    private final List<ReviewKeyword> data = new ArrayList<>();
//
//    @Override
//    public List<KeywordCount> findTopKeywordsByStadiumIdAndBlockCode(
//            Long stadiumId, String blockCode, int limit) {
//        Map<String, Long> keywordCounts =
//                data.stream()
//                        .filter(
//                                keyword ->
//                                        keyword.getReview().getStadium().getId().equals(stadiumId)
//                                                && keyword.getReview()
//                                                        .getBlock()
//                                                        .getCode()
//                                                        .equals(blockCode))
//                        .collect(
//                                Collectors.groupingBy(
//                                        ReviewKeyword::getContent, Collectors.counting()));
//
//        return keywordCounts.entrySet().stream()
//                .map(
//                        entry ->
//                                KeywordCount.builder()
//                                        .content(entry.getKey())
//                                        .count(entry.getValue())
//                                        .build())
//                .sorted((k1, k2) -> k2.getCount().compareTo(k1.getCount()))
//                .limit(limit)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ReviewKeyword> saveAll(List<ReviewKeyword> reviewKeywords) {
//        data.addAll(reviewKeywords);
//        return reviewKeywords;
//    }
//
//    public void clear() {
//        data.clear();
//    }
// }
