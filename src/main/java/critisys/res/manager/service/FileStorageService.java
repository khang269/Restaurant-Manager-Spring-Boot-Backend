package critisys.res.manager.service;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import critisys.res.manager.model.MenuItem;

@Service
public class FileStorageService {

    private final String ROOT_DIRECTORY = "storage";

    private final String MENU_ITEM_DIRECTORY = "item";

    private final Path root = Paths.get("storage");

    public FileStorageService(){
        try {
            Files.createDirectories(Paths.get(ROOT_DIRECTORY));
            Files.createDirectories(Paths.get(ROOT_DIRECTORY, MENU_ITEM_DIRECTORY));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void saveMenuItemThumbnails(MultipartFile file, long menuItemId) {
        try {
            Files.createDirectories(Paths.get(ROOT_DIRECTORY, MENU_ITEM_DIRECTORY, "" + menuItemId));
            Files.copy(
                file.getInputStream(),
                Paths.get(ROOT_DIRECTORY, MENU_ITEM_DIRECTORY, "" + menuItemId, file.getOriginalFilename()),
                StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveMenuItemThumbnails(MultipartFile file, MenuItem menuItem) {
        try {
            Files.createDirectories(Paths.get(ROOT_DIRECTORY, MENU_ITEM_DIRECTORY, menuItem.getId()));
            Files.copy(
                file.getInputStream(),
                Paths.get(ROOT_DIRECTORY, MENU_ITEM_DIRECTORY, menuItem.getId(), menuItem.getThumbnail()),
                StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

}
