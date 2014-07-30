package cn.eclipse;
/*
 * Created on 2005-8-27
 *
 */
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

/**
 * @author lq
 * 
 */
public class ColorList extends Canvas {
	static int COLORS[] = { SWT.COLOR_RED, SWT.COLOR_GREEN, SWT.COLOR_BLUE,
			SWT.COLOR_MAGENTA, SWT.COLOR_YELLOW, SWT.COLOR_CYAN,
			SWT.COLOR_DARK_RED, SWT.COLOR_DARK_GREEN, SWT.COLOR_DARK_BLUE,
			SWT.COLOR_DARK_MAGENTA, SWT.COLOR_DARK_YELLOW, SWT.COLOR_DARK_CYAN };

	static String COLORSNAME[] = { "��ɫ", "��ɫ", "��ɫ", "��ɫ", "��ɫ",
		"��ɫ", "����ɫ", "����ɫ", "����ɫ", "����ɫ",
		"����ɫ", "����ɫ" };

	Vector colors = new Vector();

	Vector colorNames = new Vector();

	int rowSel = -1;

	int oldRowSel = -1;

	int cx, cy;

	int maxX = 200, maxY;

	int lineHeight = 18;

	Vector selectionListeners = new Vector();

	public ColorList(Composite parent, int style) {
		super(parent, style);
		init();
	}

	void init() {
		cx = 0;
		cy = 0;
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				Point size = getSize();
				int beginx = e.x;
				int beginy = (e.y / lineHeight) * lineHeight;
				int beginLine = (e.y - cy) / lineHeight;
				int endLine = beginLine + e.height / lineHeight + 1;
				if (endLine > getItemCount())
					endLine = getItemCount();
				for (int i = beginLine; i < endLine; i++) {
					boolean selected = false;
					if (i == rowSel)
						selected = true;
					onPaint(gc, i, cx, beginy + (i - beginLine) * lineHeight,
							selected);
				}

			}
		});
		addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {

			}

			public void mouseDown(MouseEvent e) {
				int row = (e.y - cy) / lineHeight;
				if (row >= 0) {
					oldRowSel = rowSel;
					rowSel = row;
				}
				if (oldRowSel != rowSel) {
					((Canvas) e.getSource()).redraw(cx, (e.y / lineHeight)
							* lineHeight, maxX, lineHeight, false);
					((Canvas) e.getSource()).redraw(cx, (oldRowSel + cy
							/ lineHeight)
							* lineHeight, maxX, lineHeight, false);
				}
				selectionChanged();
			}

			public void mouseUp(MouseEvent e) {

			}

		});
		addListener(SWT.KeyDown, new Listener() {
			public void handleEvent(Event event) {
				switch (event.keyCode) {
				case SWT.ARROW_UP: // up arrow key
					if (rowSel != 0) {
						oldRowSel = rowSel;
						rowSel--;
						if (oldRowSel != rowSel) {
							((Canvas) event.widget).redraw(cx, rowSel*lineHeight + cy
									, maxX, lineHeight*2, false);
						}
						if (rowSel < -cy / lineHeight) {
							ScrollBar bar = ((Canvas) event.widget)
									.getVerticalBar();
							bar.setSelection(bar.getSelection() - lineHeight);
							scrollVertical(bar);
						}
						selectionChanged();
					}
					break;
				case SWT.ARROW_DOWN: // down arror key
					if (rowSel < colors.size() - 1) {
						oldRowSel = rowSel;
						rowSel++;
						if (oldRowSel != rowSel) {
							((Canvas) event.widget).redraw(cx, (rowSel + cy
									/ lineHeight)
									* lineHeight, maxX, lineHeight, false);
							((Canvas) event.widget).redraw(cx, (oldRowSel + cy
									/ lineHeight)
									* lineHeight, maxX, lineHeight, false);
						}
						if (rowSel >= (((Canvas) event.widget).getClientArea().height - cy)
								/ lineHeight) {
							ScrollBar bar = ((Canvas) event.widget)
									.getVerticalBar();
							if (bar != null) {
								bar.setSelection(bar.getSelection()
										+ lineHeight);
								scrollVertical(bar);
							}
						}
						selectionChanged();
					}
					break;
				}
			}
		});
		addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent event) {
				Point size = getSize();
				maxX = size.x * 3 / 2;
				maxY = colors.size() * lineHeight;
				resizeScrollBars();
			}
		});
		addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				((Canvas) e.getSource()).redraw(cx, rowSel * lineHeight, maxX,
						lineHeight, true);
			}

			public void focusLost(FocusEvent e) {
				((Canvas) e.getSource()).redraw(cx, rowSel * lineHeight, maxX,
						lineHeight, true);
			}
		});
		addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_TAB_NEXT
						|| e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
					e.doit = true;
				}
			};

		});
		ScrollBar bar = getHorizontalBar();
		if (bar != null) {
			bar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					scrollHorizontal((ScrollBar) event.widget);
				}
			});
		}
		bar = getVerticalBar();
		if (bar != null) {
			bar.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					scrollVertical((ScrollBar) event.widget);
				}
			});
		}
		resizeScrollBars();
		addAccessibility();
	}
	
	private void addAccessibility(){
		Accessible accessible = getAccessible();
	    accessible.addAccessibleControlListener(new AccessibleControlAdapter() {
	        public void getRole(AccessibleControlEvent e) {
	        	int role = 0;
	            int childID = e.childID;
	            if (childID == ACC.CHILDID_SELF) {
	                role = ACC.ROLE_LIST;
	            } else if (childID >= 0 && childID < colors.size()) {
	                role = ACC.ROLE_LISTITEM;
	            }
	            e.detail = role;
	        }
	        
	        public void getValue(AccessibleControlEvent e){
	        	int childID = e.childID;
	            if (childID == ACC.CHILDID_SELF) {
	                e.result = getText();
	            } else if (childID >= 0 && childID < colors.size()) {
	                e.result = (String)colorNames.get(childID);
	            }
	        }
	        
	        public void getChildAtPoint(AccessibleControlEvent e) {
	        	Point testPoint = toControl(new Point(e.x, e.y));
	            int childID = ACC.CHILDID_NONE;
	            childID = (testPoint.y - cy)/lineHeight; 
	            if (childID == ACC.CHILDID_NONE) {
	                Rectangle location = getBounds();
	                location.height = location.height - getClientArea().height;
	                if (location.contains(testPoint)) {
	                    childID = ACC.CHILDID_SELF;
	                }
	            }
	            e.childID = childID;

	        }
	        
	        public void getLocation(AccessibleControlEvent e) {
	        	Rectangle location = null;
	            int childID = e.childID;
	            if (childID == ACC.CHILDID_SELF) {
	                location = getBounds();
	            }
	            if (childID >= 0 && childID < colors.size()) {
	                location = new Rectangle(cx,childID*lineHeight+cy,maxX,lineHeight);
	            }
	            if (location != null) {
	                Point pt = toDisplay(new Point(location.x, location.y));
	                e.x = pt.x;
	                e.y = pt.y;
	                e.width = location.width;
	                e.height = location.height;
	            }

	        }
	        
	        public void getChildCount(AccessibleControlEvent e) {
	            e.detail = colors.size();
	        }
	        
	        public void getState(AccessibleControlEvent e) {
	        	int state = 0;
	            int childID = e.childID;
	            if (childID == ACC.CHILDID_SELF) {
	                state = ACC.STATE_NORMAL;
	            } else if (childID >= 0 && childID < colors.size()) {
	                state = ACC.STATE_SELECTABLE;
	                if (isFocusControl()) {
	                    state |= ACC.STATE_FOCUSABLE;
	                }
	                if (rowSel == childID) {
	                    state |= ACC.STATE_SELECTED;
	                    if (isFocusControl()) {
	                        state |= ACC.STATE_FOCUSED;
	                    }
	                }
	            }
	            e.detail = state;

	        }
	        
	    });
	}

	void onPaint(GC gc, int row, int beginx, int beginy, boolean isSelected) {
		Color initColor = gc.getBackground();
		Color initForeColor = gc.getForeground();
		if (isSelected) {
			gc.setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_SELECTION));
			gc.fillRectangle(beginx, beginy, maxX, lineHeight);
			gc.setForeground(Display.getCurrent().getSystemColor(
					SWT.COLOR_LIST_SELECTION_TEXT));
		} else {
			gc.setBackground(initColor);
		}
		gc.drawString((String) colorNames.get(row), beginx + 40, beginy);
		Color color = Display.getCurrent().getSystemColor(
				((Integer) colors.get(row)).intValue());
		gc.setBackground(color);
		gc.fillRectangle(beginx + 2, beginy + 2, 30, lineHeight - 4);
		gc.setBackground(initColor);
		gc.setForeground(initForeColor);
		if (isFocusControl() && isSelected)
			gc.drawFocus(cx, beginy, maxX, lineHeight);
	}

	void resizeScrollBars() {
		Rectangle clientArea = getClientArea();
		ScrollBar hbar = getHorizontalBar();
		if (hbar != null) {
			hbar.setMaximum(maxX);
			hbar.setThumb(clientArea.width);
			hbar.setPageIncrement(clientArea.width);
		}

		ScrollBar vbar = getVerticalBar();
		if (vbar != null) {
			vbar.setMaximum(maxY);
			vbar.setThumb(clientArea.height);
			vbar.setPageIncrement(clientArea.height);
			vbar.setIncrement(lineHeight);
			if (clientArea.height >= lineHeight * getItemCount() + 2)
				vbar.setVisible(false);
			else
				vbar.setVisible(true);
		}
	}

	void scrollHorizontal(ScrollBar scrollBar) {
		Rectangle bounds = getClientArea();
		int x = -scrollBar.getSelection();
		if (x + maxX < bounds.width) {
			x = bounds.width - maxX;
		}
		scroll(x, cy, cx, cy, maxX, maxY, false);
		cx = x;
	}

	void scrollVertical(ScrollBar scrollBar) {
		Rectangle bounds = getClientArea();
		int y = -scrollBar.getSelection();
		if (y + maxY < bounds.height) {
			y = bounds.height - maxY;
		}
		if( y%lineHeight !=0 )
			y = y - y % lineHeight - lineHeight;
		scroll(cx, y, cx, cy, maxX, maxY, false);
		cy = y;
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		int width = 300, height = lineHeight * (getItemCount())+50;
		if (wHint != SWT.DEFAULT)
			width = wHint;
		if (hHint != SWT.DEFAULT)
			height = hHint;
		return new Point(width + 2, height + 2);

	}

	public void setSelection(int index) {
		if (index >= getItemCount() || index < 0)
			return;
		oldRowSel = rowSel;
		rowSel = index;
		selectionChanged();
	}

	public int getSelectionIndex() {
		return rowSel;
	}

	public int getItemHeight() {
		return lineHeight;
	}

	public void setItemHeight(int height) {
		lineHeight = height;
	}

	public int getItemCount() {
		return colors.size();
	}

	public void add(int colorIndex, String colorName) {
		colorNames.add(colorName);
		colors.add(new Integer(colorIndex));
	}

	public void setDefault() {
		for (int i = 0; i < COLORS.length; i++) {
			colors.add(new Integer(COLORS[i]));
			colorNames.add(COLORSNAME[i]);
		}
	}

	public void addSelectionListener(SelectionListener listener) {
		selectionListeners.addElement(listener);
	}

	public void removeImageClickedListener(SelectionListener listener) {
		selectionListeners.removeElement(listener);
	}

	public void selectionChanged() {
		Event event = new Event();
		event.widget = this;
		SelectionEvent e = new SelectionEvent(event);
		for (int i = 0; i < selectionListeners.size(); i++) {
			SelectionListener listener = (SelectionListener) selectionListeners.elementAt(i);
			listener.widgetSelected(e);
		}

	}

	public String getText(){
		if(rowSel>=0)
			return (String)colorNames.get(rowSel);
		else return null;
	}
}