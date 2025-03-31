package space.dinhphatphat.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import space.dinhphatphat.model.Story;
import space.dinhphatphat.model.User;
import space.dinhphatphat.repository.StoryRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    public List<Story> findAll(){
        return storyRepository.findAll();
    }

    public List<Story> findAllOrderByUpdatedAtDesc(){
        return storyRepository.findAllByOrderByUpdatedAtDesc();
    }

    public List<Story> findAllByUserId(int userId){
        return storyRepository.findAllByUser_Id(userId);
    }

    public List<Story> findTop3ByOrderByCreatedAtDesc(){
        return storyRepository.findTop3ByOrderByCreatedAtDesc();
    }

    public Story findById(int id){
        return storyRepository.findById(id).orElse(null);
    }

    public Story findByMeta(String meta){
        return storyRepository.findByMeta(meta);
    }
    public Story create(Story story, MultipartFile image) throws IOException {
        try{
            Story savedStory = storyRepository.save(story);
            if(image != null){
                String imagePath = upLoadImage(image, savedStory.getId());
                savedStory.setImagePath(imagePath);
            }
            savedStory.setMetaWithId();
            return storyRepository.save(savedStory);
        }
        catch(Exception e){
            return null;
        }
    }

    public String upLoadImage(MultipartFile imageFile, int storyId) throws IOException {
        String uploadDir;
        String accessPath;

        uploadDir = "uploads/image/story/";
        accessPath = "/uploads/image/story/";

        return UploadService.upLoadImage(imageFile, storyId, uploadDir, accessPath);
    }

    public Story update(Story story, MultipartFile image) throws IOException {
        try{
            if(image != null){
                String imagePath = upLoadImage(image, story.getId());
                story.setImagePath(imagePath);
            }
            return storyRepository.save(story);
        }
        catch(Exception e){
            return null;
        }
    }

    public Page<Story> findAll(Pageable pageable) {
        return storyRepository.findAll(pageable);
    }

    public Page<Story> searchStories(String search, Pageable pageable) {
        return storyRepository.findByTitleContainingIgnoreCase(search, pageable);
    }

    public void deleteById(int id) {
        storyRepository.deleteById(id);
    }

}
