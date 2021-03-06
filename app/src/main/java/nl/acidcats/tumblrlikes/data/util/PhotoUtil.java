package nl.acidcats.tumblrlikes.data.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.acidcats.tumblrlikes.data.vo.db.PhotoEntity;
import nl.acidcats.tumblrlikes.data.vo.tumblr.TumblrLikeVO;
import nl.acidcats.tumblrlikes.data.vo.tumblr.TumblrPhotoPostVO;
import nl.acidcats.tumblrlikes.data.vo.tumblr.TumblrPhotoVO;

/**
 * Created by stephan on 11/04/2017.
 */

public class PhotoUtil {
    public static List<PhotoEntity> toPhotoEntities(TumblrLikeVO likeVO) {
        List<TumblrPhotoPostVO> postVOs = likeVO.photos();
        if (postVOs == null || postVOs.size() == 0) return null;

        List<PhotoEntity> photoEntities = new ArrayList<>();

        for (TumblrPhotoPostVO postVO : postVOs) {
            List<TumblrPhotoVO> photos = new ArrayList<>();

            // add original photo
            if (postVO.originalPhoto() != null) {
                photos.add(postVO.originalPhoto());
            }

            // add alt photos
            if (postVO.altPhotos() != null) {
                photos.addAll(postVO.altPhotos());
            }

            // skip if none were found
            if (photos.size() == 0) continue;

            // sort by size
            Collections.sort(photos, new PhotoSizeComparator());

            // store biggest
            photoEntities.add(new PhotoEntity(photos.get(0).url(), likeVO.id()));
        }

        return photoEntities;
    }

    private static class PhotoSizeComparator implements java.util.Comparator<TumblrPhotoVO> {
        @Override
        public int compare(TumblrPhotoVO photo1, TumblrPhotoVO photo2) {
            long sizeDiff = photo1.getSize() - photo2.getSize();

            // sort descending
            if (sizeDiff == 0) return 0;
            if (sizeDiff < 0) return 1;
            return -1;
        }
    }
}
