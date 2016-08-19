package com.myapp.auth

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UserRole implements Serializable {

	private static final long serialVersionUID = 1

	User person
	Role role

	UserRole(User u, Role r) {
		this()
		person = u
		role = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof UserRole)) {
			return false
		}

		other.person?.id == person?.id && other.role?.id == role?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (person) builder.append(person.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static UserRole get(long personId, long roleId) {
		criteriaFor(personId, roleId).get()
	}

	static boolean exists(long personId, long roleId) {
		criteriaFor(personId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(long personId, long roleId) {
		UserRole.where {
			person == User.load(personId) &&
			role == Role.load(roleId)
		}
	}

	static UserRole create(User user, Role role, boolean flush = false) {
		def instance = new UserRole(person: user, role: role)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(User u, Role r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = UserRole.where { person == u && role == r }.deleteAll()

		if (flush) { UserRole.withSession { it.flush() } }

		rowCount
	}

	static void removeAll(User u, boolean flush = false) {
		if (u == null) return

		UserRole.where { person == u }.deleteAll()

		if (flush) { UserRole.withSession { it.flush() } }
	}

	static void removeAll(Role r, boolean flush = false) {
		if (r == null) return

		UserRole.where { role == r }.deleteAll()

		if (flush) { UserRole.withSession { it.flush() } }
	}

	static constraints = {
		role validator: { Role r, UserRole ur ->
			if (ur.person == null || ur.person.id == null) return
			boolean existing = false
			UserRole.withNewSession {
				existing = UserRole.exists(ur.person.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['person', 'role']
		version false
	}
}
