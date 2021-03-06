package org.scajadoc.frontend

import tools.nsc.doc.model.{MemberEntity, DocTemplateEntity}

/**
 * Container holding basic entity queries. 
 *
 * @author Filip Rogaczewski
 */
object entityQueryContainer {

	/**
	 * Returns true if the entity is a root package.
	 */
	val isRootPackage : (MemberEntity => Boolean) =
		(entity : MemberEntity) => (entity.isInstanceOf[DocTemplateEntity]
				&& entity.asInstanceOf[DocTemplateEntity].isRootPackage)

	/**
	 * Returns true if the entity is a package.
	 */
	val isPackage : (MemberEntity => Boolean) =
		(entity : MemberEntity) => (entity.isInstanceOf[DocTemplateEntity]
				&& entity.asInstanceOf[DocTemplateEntity].isPackage
				&& !isRootPackage(entity))

	/**
	 * Returns true if the entity a class.
	 */
	val isType : (MemberEntity => Boolean) =
		(entity : MemberEntity) => (entity.isInstanceOf[DocTemplateEntity]
				&& !entity.asInstanceOf[DocTemplateEntity].isPackage)

	val isInterface : (DocTemplateEntity => Boolean) =
		(entity : DocTemplateEntity) => entity.isTrait

	/**
	 * Returns true if the entity is subclass of given class.
	 */
	val isSubclassOf : ((DocTemplateEntity, Class[_]) => Boolean) =
		(entity : DocTemplateEntity, clazz : Class[_]) =>
				entity.linearizationTemplates.exists(_.qualifiedName == clazz.getCanonicalName)

	/**
	 * Returns true if the entity is an exception.
	 */
	val isException : (DocTemplateEntity => Boolean) =
		(t : DocTemplateEntity) => isSubclassOf(t, classOf[java.lang.Exception])

	/**
	 * Returns true if the entity is an error.
	 */
	val isError : (DocTemplateEntity => Boolean) =
		(t : DocTemplateEntity) => isSubclassOf(t, classOf[java.lang.Error])

	/**
	 * Returns true if the entity is an annotation.
	 */
	val isAnnotation : (DocTemplateEntity => Boolean) =
		(t : DocTemplateEntity) => isSubclassOf(t, classOf[scala.Annotation])

	val isEnumeration : (DocTemplateEntity => Boolean) =
		(t : DocTemplateEntity) => isSubclassOf(t, classOf[scala.Enumeration])


}