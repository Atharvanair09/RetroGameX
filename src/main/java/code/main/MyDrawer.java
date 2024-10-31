package code.main;

import code.panels.*;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.MenuValidation;
import raven.drawer.component.menu.SimpleMenuOption;
import raven.swing.AvatarIcon;


class MyDrawer extends SimpleDrawerBuilder
{
    @Override
    public SimpleHeaderData getSimpleHeaderData()
    {
        return new SimpleHeaderData()
                .setIcon(new AvatarIcon(getClass().getResource("src/main/java/images/logo.png"), 60, 60, 999))
                .setTitle("Account")
                .setDescription("atharvanair09.ns@gmail.com");
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption()
    {
        String Menus[][] = 
        {
            {"~Games~"},
            {"Pacman"},
            {"Flappy Bird"},
            {"Snake"},
        };
        
        return new SimpleMenuOption()
                .setMenus(Menus)
                .addMenuEvent(new MenuEvent()
                {
                  @Override
                  public void selected(MenuAction action, int index, int subIndex)
                  {

                     if(index==0)
                     {
                        pacmanf pacman = new pacmanf();
                        pacman.setVisible(true);
                        pacman.setLocationRelativeTo(null);

                     }
                     else if(index==1)
                     {
                         flappybirdf flappy = new flappybirdf();
                         flappy.setVisible(true);
                         flappy.setLocationRelativeTo(null);
                     }
                     else if(index==2)
                     {
                         snakef snake = new snakef();
                         snake.setVisible(true);
                         snake.setLocationRelativeTo(null);
                     }
                  }
                })
                .setMenuValidation(new MenuValidation() 
                {
                    @Override
                    public boolean menuValidation(int index, int subIndex) 
                    {
                        return true;
                    }
                });
    }
     
    @Override
    public SimpleFooterData getSimpleFooterData()
    {
        return new SimpleFooterData().setTitle("RetroGameX").setDescription("Version 1.0.0");
    }
    
    @Override
    public int getDrawerWidth() 
    {
        return 275;
    }
    
} 
