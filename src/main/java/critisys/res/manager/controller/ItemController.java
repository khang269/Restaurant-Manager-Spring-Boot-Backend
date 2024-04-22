package critisys.res.manager.controller;

import java.util.List;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import critisys.res.manager.model.EntityState;
import critisys.res.manager.model.Menu;
import critisys.res.manager.model.MenuItem;
import critisys.res.manager.service.FileStorageService;
import critisys.res.manager.service.MenuItemService;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMethod;



@RestController
@RequestMapping("/api/item")
public class ItemController {
    
    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/")
    public ResponseEntity<List<MenuItem>> all() {
        ResponseEntity entity = ResponseEntity.ok().body(menuItemService.getAllMenuItem());
        return entity;
    }

    @GetMapping("/active")
    public ResponseEntity<List<MenuItem>> getActiveItemByMenuId(@RequestParam String menuId) {
        ResponseEntity entity = ResponseEntity.ok().body(menuItemService.getAllActiveItemByMenuId(menuId));
        return entity;
    }
    
    @GetMapping("/deleted")
    public ResponseEntity<List<MenuItem>> getDeletedItemByMenuId(@RequestParam String menuId) {
        ResponseEntity entity = ResponseEntity.ok().body(menuItemService.getAllDeletedItemByMenuId(menuId));
        return entity;
    }

    @PostMapping("/upsert")
    public ResponseEntity upsertMenuItem(@RequestBody MenuItem item) {
        item.setState(EntityState.ACTIVE);
        try{
            ResponseEntity entity = ResponseEntity.ok().body(menuItemService.saveMenuItem(item));
            return entity;
        }
        catch (Exception e){
            ResponseEntity entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
            return entity;
        }
    }

    // @RequestMapping(value = "/item/upload", 
    //             consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
    //             produces = MediaType.APPLICATION_JSON_VALUE,
    //             method = {RequestMethod.POST})
    // public ResponseEntity uploadMenuItem(@RequestPart MenuItem item, @RequestPart MultipartFile thumbnail){
    //     try{
    //         MenuItem savedItem = menuItemService.saveMenuItem(item);
    //         fileStorageService.saveMenuItemThumbnails(thumbnail, savedItem);
    //         ResponseEntity entity = ResponseEntity.ok().body(menuItemService.saveMenuItem(item));
    //         return entity;
    //     }
    //     catch (Exception e){
    //         ResponseEntity entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    //         return entity;
    //     }
    // }

    @RequestMapping(value = "/upload", 
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
                produces = MediaType.APPLICATION_JSON_VALUE,
                method = {RequestMethod.POST})
    public ResponseEntity uploadMenuItem(@ModelAttribute MenuItemFormWrapper item){
        try{

            MenuItem menuItem = new MenuItem();
            menuItem.setId(item.getId());
            menuItem.setState(item.getState());
            menuItem.setName(item.getName());
            menuItem.setPrice(item.getPrice());
            menuItem.setThumbnail(item.getThumbnail());

            MenuItem savedItem = menuItemService.saveMenuItem(menuItem, item.getMenuId());
            fileStorageService.saveMenuItemThumbnails(item.getFile(), savedItem);
            ResponseEntity entity = ResponseEntity.ok().body(savedItem);
            return entity;
        }
        catch (Exception e){
            ResponseEntity entity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
            return entity;
        }
    }

    public static class MenuItemFormWrapper {
        private String id;

        private String menuId;

        private String name = "";

        private Float price = 1000.0f;

        private String thumbnail = "";

        private EntityState state = EntityState.ACTIVE;
        
        private MultipartFile file;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Float getPrice() {
            return price;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public MultipartFile getFile() {
            return file;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }

        public EntityState getState() {
            return state;
        }

        public void setState(EntityState state) {
            this.state = state;
        }

        public String getMenuId() {
            return menuId;
        }

        public void setMenuId(String menuId) {
            this.menuId = menuId;
        }

        
    }

}
