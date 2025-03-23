package space.dinhphatphat.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;

    public List<Story> findAll(){
        return storyRepository.findAll();
    }

    public List<Story> findAllByUserId(int userId){
        return storyRepository.findAllByUser_Id(userId);
    }

    public List<Story> findTop3ByOrderByCreatedAtDesc(){
        return storyRepository.findTop3ByOrderByCreatedAtDesc();
    }

    public Story create(Story story, MultipartFile image) throws IOException {
        try{
            if(!image.isEmpty()){
                String imagePath = upLoadImage(image, story.getId());
                story.setImagePath(imagePath);
            }
            return storyRepository.save(story);
        }
        catch(Exception e){
            return null;
        }
    }

    public String upLoadImage(MultipartFile imageFile, int storyId) throws IOException {
        // Check the environment (Dev or Deploy)
        String uploadDir;
        String accessPath;
        //If Dev
        if (new File("src/main/resources/static").exists()) {
            // Dev: resources/static/images/home/
            uploadDir = "src/main/resources/static/images/story/";
            accessPath = "/images/story/";
        }
        //If Deploy
        else {
            // Deploy: Outside direct (uploads/)
            uploadDir = "uploads/images/story/";
            accessPath = "/uploads/images/story/";

        }

        //Take file extension
        String fileExtension = "";
        // dot index
        int dotIndex = imageFile.getOriginalFilename().lastIndexOf(".");
        // Check if dot index exists, then take file extension by substring method
        if (dotIndex > 0) {
            fileExtension = imageFile.getOriginalFilename().substring(dotIndex);
        }

        //File name: <home id>.<file extension> (Ex: H01.png)
        String fileName = storyId + fileExtension;
        //Upload path
        File uploadPath = new File(uploadDir);

        // Make direct path if not exists
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // Save file into path
        Path destination = Path.of(uploadDir + fileName);

        Files.copy(imageFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return accessPath + fileName;
    }

    public Story update(Story story, MultipartFile image) throws IOException {
        try{
            if(!image.isEmpty()){
                String imagePath = upLoadImage(image, story.getId());
                story.setImagePath(imagePath);
            }
            return storyRepository.save(story);
        }
        catch(Exception e){
            return null;
        }
    }

}
