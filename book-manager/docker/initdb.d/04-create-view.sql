USE book_manager;

CREATE VIEW book_with_rental AS
    SELECT
        b.id,
        b.title,
        b.author,
        b.release_date,
        r.user_id,
        r.rental_datetime,
        r.return_deadline
FROM book AS b
    LEFT OUTER JOIN rental AS r ON b.id = r.book_id;