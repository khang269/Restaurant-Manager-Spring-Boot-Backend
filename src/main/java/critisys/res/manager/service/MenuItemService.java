package critisys.res.manager.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import critisys.res.manager.model.EntityState;
import critisys.res.manager.model.MenuItem;
import critisys.res.manager.respository.MenuItemRepository;
import critisys.res.manager.respository.MenuRepository;

@Service
public class MenuItemService {
    
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired MenuRepository menuRepository;

    public List<MenuItem> getAllMenuItem(){
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getAllActiveItemByMenuId(String menuId){
        return menuItemRepository.findByMenuIdAndState(menuId, EntityState.ACTIVE);
    }

    public List<MenuItem> getAllDeletedItemByMenuId(String menuId){
        return menuItemRepository.findByMenuIdAndState(menuId, EntityState.DELETED);
    }

    public List<MenuItem> getAllDeletedItems(){
        return menuItemRepository.findByState(EntityState.DELETED);
    }

    public MenuItem saveMenuItem(MenuItem menuItem, String menuId) throws Exception{
        
        Optional<MenuItem> existOptional = menuItemRepository.findById(menuItem.getId());
        MenuItem exist;
        if(existOptional.isPresent()){
            exist = existOptional.get();
        }
        else{
            exist = new MenuItem();
        }

        if(menuRepository.findById(menuId).isPresent()){
            exist.setMenu(menuRepository.findById(menuId).get());
            exist.setName(menuItem.getName());
            exist.setPrice(menuItem.getPrice());
            exist.setThumbnail(menuItem.getThumbnail());
            exist.setUpdatedDate(new Date());
            exist.setState(menuItem.getState());
            menuItemRepository.save(exist);
            return exist;
        }else{
            throw new Exception("No menu found for this item");
        }

    }

    public MenuItem saveMenuItem(MenuItem menuItem) throws Exception{
        
        Optional<MenuItem> existOptional = menuItemRepository.findById(menuItem.getId());
        MenuItem exist;
        if(existOptional.isPresent()){
            exist = existOptional.get();
        }
        else{
            exist = new MenuItem();
        }

        if(menuRepository.findById(menuItem.getMenu().getId()).isPresent()){
            exist.setMenu(menuRepository.findById(menuItem.getMenu().getId()).get());
            exist.setName(menuItem.getName());
            exist.setPrice(menuItem.getPrice());
            exist.setThumbnail(menuItem.getThumbnail());
            exist.setUpdatedDate(new Date());
            exist.setState(menuItem.getState());
            menuItemRepository.save(exist);
            return exist;
        }else{
            throw new Exception("No menu found for this item");
        }

    }
}
