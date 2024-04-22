package critisys.res.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import critisys.res.manager.model.EntityState;
import critisys.res.manager.model.Menu;
import critisys.res.manager.respository.MenuRepository;
import critisys.res.manager.service.MenuService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/menu")
public class MenuController {
    
    @Autowired
    private MenuService menuService;

    @GetMapping("/")
    public ResponseEntity<List<Menu>> all() {
        ResponseEntity entity = ResponseEntity.ok()
        .body(menuService.getAllMenu());
        return entity;
    }

    @PostMapping("/upsert")
    public ResponseEntity upsert(@RequestBody Menu menu) {
        menu.setState(EntityState.ACTIVE);
        Menu savedMenu = menuService.upsertMenu(menu);
        return ResponseEntity.ok().body(savedMenu);
    }
    
    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody Menu menu) {
        try {
            Menu deleted  = menuService.deleteMenu(menu);
            return ResponseEntity.ok().body(deleted);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
    
}
