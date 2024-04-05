package critisys.res.manager.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import critisys.res.manager.model.EntityState;
import critisys.res.manager.model.Menu;
import critisys.res.manager.respository.MenuRepository;

@Service
public class MenuService {
    
    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenu(){
        return menuRepository.findAll();
    }

    public List<Menu> getAllActiveMenu(){
        return menuRepository.findByState(EntityState.ACTIVE);
    }

    public List<Menu> getAllDeletedMenu(){
        return menuRepository.findByState(EntityState.DELETED);
    }

    public Menu upsertMenu(Menu menu){
        Optional<Menu> exist = menuRepository.findById(menu.getId());
        if (exist.isPresent()) {
            Menu existItem = exist.get();
            existItem.setName(menu.getName());
            existItem.setUpdatedDate(new Date());
            menuRepository.save(existItem);
            return existItem;    
        }
        else{
            Menu newItem = new Menu();
            newItem.setCreatedDate(new Date());
            newItem.setUpdatedDate(new Date());
            newItem.setName(menu.getName());
            menuRepository.save(newItem);
            return newItem;    
        }
    }

    public Menu deleteMenu(Menu menu) throws Exception{
        Optional<Menu> exist = menuRepository.findById(menu.getId());
        if (exist.isPresent()) {
            Menu existItem = exist.get();
            existItem.setUpdatedDate(new Date());
            existItem.setState(EntityState.DELETED);
            menuRepository.save(existItem);
            return existItem;    
        }
        else{
            throw new Exception("No menu to deleted");    
        }
    }
}
