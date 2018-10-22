package com.yibi.common.model;

import java.util.List;

public class Tree implements java.io.Serializable {

	private static final long serialVersionUID = -8874026246944902073L;
	/**
	 * 绑定到节点的标识值。
	 */
	private String id;
	/**
	 * 要显示的文本。
	 */
	private String text;
	/**
	 * 节点状态，'open' 或 'closed'。
	 */
	private String state = "open";
	/**
	 * 节点是否被选中。
	 */
	private boolean checked = false;
	/**
	 * 绑定到节点的自定义属性。
	 */
	private Json attributes;
	/**
	 * 下级列表
	 */
	private List<Tree> children;
	/**
	 * 用来显示图标的 css class。
	 */
	private String iconCls;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Json getAttributes() {
		return attributes;
	}

	public void setAttributes(Json attributes) {
		this.attributes = attributes;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

}
