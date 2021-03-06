/*******************************************************************************
 * Copyright (c) 2012 Rushan R. Gilmullin and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Rushan R. Gilmullin - initial API and implementation
 *******************************************************************************/

package org.semanticsoft.vaaclipse.widgets;

import java.util.Iterator;

import org.semanticsoft.commons.geom.Bounds;
import org.semanticsoft.vaadinaddons.boundsinfo.BoundsinfoVerticalLayout;
import org.semanticsoft.vaadinaddons.boundsinfo.BoundsinfoVerticalLayout.BoundsUpdateListener;

import com.vaadin.ui.AbstractSplitPanel;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.ResizeEvent;

/**
 * @author rushan
 * 
 */
public class WorkbenchWindow extends Window
{
	private VerticalLayout windowBody;
	private BoundsinfoVerticalLayout windowCenterArea;
	private HorizontalLayout helperLayout;
	private GridLayout topContainerPanel;
	private VerticalLayout windowContent;
	
	private VerticalLayout leftBarContainer = new VerticalLayout(); 
	private VerticalLayout rightBarContainer = new VerticalLayout();
	private VerticalLayout topBarContainer = new VerticalLayout(); 
	private HorizontalLayout bottomBarContainer = new HorizontalLayout();
	
	private TopbarComponent topbar = new TopbarComponent();
	
	private boolean boundsValide = false;
	
	public WorkbenchWindow()
	{
//		leftBarContainer.setVisible(false);
//		rightBarContainer.setVisible(false);
//		topBarContainer.setVisible(false);
//		bottomBarContainer.setVisible(false);
		
		//leftBarContainer.setSizeFull();
		leftBarContainer.setWidth(-1);
		leftBarContainer.setHeight("100%");
		
		rightBarContainer.setWidth(-1);
		rightBarContainer.setHeight("100%");
		
		topBarContainer.setHeight(-1);
		topBarContainer.setWidth("100%");
		topBarContainer.setMargin(false);
		
		//bottomBarContainer.setSizeFull();
		bottomBarContainer.setHeight(-1);
		bottomBarContainer.setWidth("100%");
		bottomBarContainer.setMargin(false);
		
		windowContent = new VerticalLayout();
		windowContent.setSizeFull();
		this.setContent(windowContent);
		
		windowBody = new VerticalLayout();
		windowBody.setSizeFull();
		windowContent.addComponent(windowBody);
		windowContent.setExpandRatio(windowBody, 100);
		
		windowCenterArea = new BoundsinfoVerticalLayout();
		windowCenterArea.setEnableBoundsUpdate(true); //enable bounds update
		windowCenterArea.setSizeFull();
		windowCenterArea.addBoundsUpdateListener(new BoundsUpdateListener() {
			
			public void processEvent(BoundsinfoVerticalLayout layout) {
				invalidateBounds();
			}
		});

		
		helperLayout = new HorizontalLayout();
		helperLayout.setSizeFull();
		
		//Top panel - it contains the top trimbar and the perspective stack panel
		topContainerPanel = new GridLayout(2, 1);
		topContainerPanel.setColumnExpandRatio(0, 100);
		topContainerPanel.setColumnExpandRatio(1, 0);
		topContainerPanel.setSizeUndefined();
		topContainerPanel.setWidth("100%");
		
		windowBody.addComponent(topContainerPanel);
		//------------------------
		helperLayout.addComponent(leftBarContainer);
		helperLayout.addComponent(windowCenterArea);
		helperLayout.addComponent(rightBarContainer);
		helperLayout.setExpandRatio(windowCenterArea, 100);
		
		windowBody.addComponent(topBarContainer);
		windowBody.addComponent(helperLayout);
		windowBody.setExpandRatio(helperLayout, 100);
		windowBody.addComponent(bottomBarContainer);
		//-------------------------------------------------------------------
	}
	
	public BoundsinfoVerticalLayout getClientArea()
	{
		return windowCenterArea;
	}
	
	public void setMenuBar(MenuBar menuBar)
	{
		for (int i = 0; i < windowContent.getComponentCount(); i++)
		{
			Component c = windowContent.getComponent(i);
			if (c instanceof MenuBar)
				windowContent.removeComponent(c);
		}
		
		menuBar.setWidth("100%");
		windowContent.addComponent(menuBar, 0);
	}
	
	public HorizontalLayout getPerspectiveStackPanel()
	{
		return (HorizontalLayout) topContainerPanel.getComponent(0, 1);
	}
	
	public void setPerspectiveStackPanel(HorizontalLayout perspectiveStackPanel)
	{
		if (perspectiveStackPanel == null)
		{
			this.topContainerPanel.removeComponent(1, 0);
		}
		else
		{
			perspectiveStackPanel.setSizeUndefined();
			this.topContainerPanel.addComponent(perspectiveStackPanel, 1, 0);	
		}
	}
	
	public void setLeftBar(Component bar)
	{
		if (bar == null)
		{
			leftBarContainer.removeAllComponents();
			return;
		}
		
		bar.setWidth(-1);
		bar.setHeight("100%");
		
		leftBarContainer.removeAllComponents();
		leftBarContainer.addComponent(bar);
	}
	
	public void setRightBar(Component bar)
	{
		if (bar == null)
		{
			rightBarContainer.removeAllComponents();
			return;
		}
		
		bar.setWidth(-1);
		bar.setHeight("100%");
		
		rightBarContainer.removeAllComponents();
		rightBarContainer.addComponent(bar);
	}
	
	public void setBottomBar(Component bar)
	{
		if (bar == null)
		{
			bottomBarContainer.removeAllComponents();
			return;
		}
		
		bar.setWidth("100%");
		bar.setHeight(-1);
		
		bottomBarContainer.removeAllComponents();
		bottomBarContainer.addComponent(bar);
	}
	
	public void setTopBar(Component bar)
	{
		if (bar == null)
		{
			this.topContainerPanel.removeComponent(topbar);
			return;
		}
		
		if (this.topContainerPanel.getComponent(0, 0) == null)
		{
			this.topContainerPanel.addComponent(topbar, 0, 0);
		}
		
		topbar.setContent(bar);
	}
	
	public TopbarComponent getTopbar() 
	{
		return topbar;
	}
	
	//-----------------------------------
	//-----------------------------------
	public boolean isBoundsValid()
	{
		return this.boundsValide;
	}
	
	public void invalidateBounds()
	{
		this.boundsValide = false;
	}
	
	public void updateWindowContentBounds()
	{
		//updateBounds(window, new Bounds(window.getPositionX(), window.getPositionY(), (int)window.getWidth(), (int)window.getHeight()));
		updateBounds(getClientArea(), getClientArea().getBounds());
		
		System.out.println("window content bounds updated!");
	}
	
	private void updateBounds(ComponentContainer container, Bounds currentBounds)
	{
		if (container instanceof BoundsinfoVerticalLayout)
		{
			BoundsinfoVerticalLayout bvl = (BoundsinfoVerticalLayout) container;
			bvl.setBounds(currentBounds);
		}
		else if (container instanceof StackWidget)
		{
			StackWidget bvl = (StackWidget) container;
			bvl.setBounds(currentBounds);
		}
		
		if (container instanceof SashWidget)
		{
			AbstractSplitPanel splitPanel = (AbstractSplitPanel) container;
			float splitPos = splitPanel.getSplitPosition() / 100;
			if (splitPanel instanceof HorizontalSplitPanel)
			{
				int firstBoundsWidth = (int)(splitPos*currentBounds.w);
				if (splitPanel.getFirstComponent() instanceof ComponentContainer)
				{
					Bounds leftBounds = new Bounds(
							currentBounds.x, 
							currentBounds.y, 
							firstBoundsWidth, 
							currentBounds.h
						);
					updateBounds((ComponentContainer) splitPanel.getFirstComponent(), leftBounds);	
				}
				
				if (splitPanel.getSecondComponent() instanceof ComponentContainer)
				{
					Bounds rightBounds = new Bounds(
							currentBounds.x + firstBoundsWidth, 
							currentBounds.y, 
							(int)(currentBounds.w - firstBoundsWidth), 
							currentBounds.h
						);
					updateBounds((ComponentContainer) splitPanel.getSecondComponent(), rightBounds);
				}
			}
			else if (splitPanel instanceof VerticalSplitPanel)
			{
				int firstBoundsHeight = (int)(splitPos*currentBounds.h);
				if (splitPanel.getFirstComponent() instanceof ComponentContainer)
				{
					Bounds leftBounds = new Bounds(
							currentBounds.x, 
							currentBounds.y, 
							currentBounds.w, 
							firstBoundsHeight
						);
					updateBounds((ComponentContainer) splitPanel.getFirstComponent(), leftBounds);	
				}
				
				if (splitPanel.getSecondComponent() instanceof ComponentContainer)
				{
					Bounds rightBounds = new Bounds(
							currentBounds.x, 
							currentBounds.y + firstBoundsHeight, 
							(int)(currentBounds.w), 
							currentBounds.h - firstBoundsHeight
						);
					updateBounds((ComponentContainer) splitPanel.getSecondComponent(), rightBounds);
				}
			}
		}
		else if (container instanceof ComponentContainer)
		{
			Iterator<Component> it = container.getComponentIterator();
			while (it.hasNext())
			{
				Component c = it.next();
				if (c instanceof ComponentContainer)
				{
					updateBounds((ComponentContainer) c, currentBounds);		
				}
			}
		}
	}
}
