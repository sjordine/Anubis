package br.unicamp.ic.anubis.ui.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.swing.KeyStroke;

import static br.unicamp.ic.anubis.ui.menu.DefaultMenuTypes.DEFAULT_MENU_ITEM;


public class MenuItemDefinition implements Comparable<MenuItemDefinition> {
	
	private String text;	
	private int order;
	private ArrayList<MenuItemDefinition> children;
	private UUID id;
	private UUID parentId;
	
	private int menuType;
	
	private KeyStroke shortcut;
	
	private MenuAction action;
	
	public MenuItemDefinition(){
		children = new ArrayList<MenuItemDefinition>();
		menuType = DEFAULT_MENU_ITEM;
	}
	
	public MenuItemDefinition(String text){
		super();
		this.setText(text);
	}
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<MenuItemDefinition> getChildren() {
		return children;
	}
	
	public void addChildren(MenuItemDefinition child){
		children.add(child);
	}

	public MenuAction getAction() {
		return action;
	}

	public void setAction(MenuAction action) {
		this.action = action;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	public void sort(){
		if (children!=null && children.size() > 0){
			Collections.sort(children);
		}
	}

	@Override
	public int compareTo(MenuItemDefinition other) {
		return ((Integer) order).compareTo(other.order);   
	}

	public KeyStroke getShortcut() {
		return shortcut;
	}

	public void setShortcut(KeyStroke shortcut) {
		this.shortcut = shortcut;
	}

	public int getMenuType() {
		return menuType;
	}

	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	private UUID getParentId() {
		return parentId;
	}

	private void setParentId(UUID parentId) {
		this.parentId = parentId;
	}


}
