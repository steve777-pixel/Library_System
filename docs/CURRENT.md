# Current Notes

- In JPA, custom Value Object primary keys (Email, BookID) should be mapped as @Embeddable + @EmbeddedId, not @Id + @Convert.
- Need to work on loan service and repository
- Controller-Service-Repository order

# Learning Notes

- @Embeddable + @EmbeddedId: Use this whenever a custom class or record is the Primary Key (@Id) of an entity (like BookID or Email).
- @Convert (AttributeConverter): Use this whenever a custom class or record is a regular non-ID column (like ISBN in Book, whether ISBN is a record or a class).
- Never use final on @Entity, @Embeddable, or @MappedSuperclass classes, nor on their persistent fields or getters