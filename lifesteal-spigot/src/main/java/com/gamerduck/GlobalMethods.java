package com.gamerduck;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.md_5.bungee.api.ChatColor;

public interface GlobalMethods {

    final ResourceBundle bundle = LifeStealMain.a().getMessagesBundle();
    default String tl(String s, Object... objects) {
    	MessageFormat messageFormat = new MessageFormat(bundle.getString(s));
    	return bundle.getString(s).equalsIgnoreCase("") ? null : color(messageFormat.format(objects).replace(' ', ' ')); 
    }
    
    final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    default String color(String textToTranslate) {
    	Matcher matcher = HEX_PATTERN.matcher(textToTranslate);
    	StringBuffer buffer = new StringBuffer();
    	while(matcher.find()) matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
    	return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    } 
    default List<String> color(List<String> s) {
    	List<String> returnList = new ArrayList<String>();
    	s.forEach(st -> {
    		returnList.add(color(st));
    	});
    	return returnList;
    }
}
