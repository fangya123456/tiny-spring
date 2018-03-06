package com.fy.example.spring.beans.config;

import com.fy.example.spring.beans.BeanDefinition;

/**
 * @author ya.fang
 * @create 2018/03/06
 **/
public class BeanDefinitionHolder implements BeanMetadataElement {

    private final BeanDefinition beanDefinition;

    private final String beanName;

    /**
     * Create a new BeanDefinitionHolder.
     * @param beanDefinition the BeanDefinition to wrap
     * @param beanName the name of the bean, as specified for the bean definition
     */
    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
    }

    /**
     * Copy constructor: Create a new BeanDefinitionHolder with the
     * same contents as the given BeanDefinitionHolder instance.
     * <p>Note: The wrapped BeanDefinition reference is taken as-is;
     * it is {@code not} deeply copied.
     * @param beanDefinitionHolder the BeanDefinitionHolder to copy
     */
    public BeanDefinitionHolder(BeanDefinitionHolder beanDefinitionHolder) {
        this.beanDefinition = beanDefinitionHolder.getBeanDefinition();
        this.beanName = beanDefinitionHolder.getBeanName();
    }


    /**
     * Return the wrapped BeanDefinition.
     */
    public BeanDefinition getBeanDefinition() {
        return this.beanDefinition;
    }

    /**
     * Return the primary name of the bean, as specified for the bean definition.
     */
    public String getBeanName() {
        return this.beanName;
    }


    /**
     * Expose the bean definition's source object.
     * @see BeanDefinition#getSource()
     */
    @Override
    public Object getSource() {
        return this.beanDefinition.getSource();
    }

    /**
     * Determine whether the given candidate name matches the bean name
     * or the aliases stored in this bean definition.
     */
    public boolean matchesName(String candidateName) {
        return (candidateName != null && (candidateName.equals(this.beanName)));
    }


    /**
     * Return a friendly, short description for the bean, stating name and aliases.
     * @see #getBeanName()
     * */
    public String getShortDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bean definition with name '").append(this.beanName).append("'");
        return sb.toString();
    }

    /**
     * Return a long description for the bean, including name and aliases
     * as well as a description of the contained {@link BeanDefinition}.
     * @see #getShortDescription()
     * @see #getBeanDefinition()
     */
    public String getLongDescription() {
        StringBuilder sb = new StringBuilder(getShortDescription());
        sb.append(": ").append(this.beanDefinition);
        return sb.toString();
    }

    /**
     * This implementation returns the long description. Can be overridden
     * to return the short description or any kind of custom description instead.
     * @see #getLongDescription()
     * @see #getShortDescription()
     */
    @Override
    public String toString() {
        return getLongDescription();
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BeanDefinitionHolder)) {
            return false;
        }
        BeanDefinitionHolder otherHolder = (BeanDefinitionHolder) other;
        return this.beanDefinition.equals(otherHolder.beanDefinition) &&
                this.beanName.equals(otherHolder.beanName);
    }

    @Override
    public int hashCode() {
        int hashCode = this.beanDefinition.hashCode();
        hashCode = 29 * hashCode + this.beanName.hashCode();
        return hashCode;
    }
}
