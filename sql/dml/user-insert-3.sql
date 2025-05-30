INSERT INTO users (
    name, student_number, nickname, grade, "group", gender, professor, phone_number,
    email, password, profile_picture, birth_date, rental_count, damage_count,
    restriction_count, report_count, role, equipment_id, bookmark_id, complaint_id,
    rental_history_id, created_at, updated_at
) VALUES
      (
          '홍길동', '20230001', '길동이', '3', 'A조', '남', '김교수', '010-1234-5678',
          'hong@example.com', 'password123', 'profile1.jpg', '2001-03-15', 5, 0,
          1, 2, 'USER', 'EQ-001', 'BM-001', 'CP-001',
          'RH-001', NOW(), NOW()
      ),
      (
          '김영희', '20230002', NULL, '2', 'B조', '여', '이교수', '010-2345-6789',
          'kim@example.com', 'securePwd!', NULL, '2002-06-22', 2, 1,
          0, 1, 'USER', 'EQ-002', 'BM-002', 'CP-002',
          'RH-002', NOW(), NOW()
      ),
      (
          '박철수', '20230003', '철수짱', '4', 'A조', '남', '최교수', '010-3456-7890',
          'park@example.com', 'cheolsu1234', 'profile3.png', '2000-12-05', 10, 3,
          2, 5, 'ADMIN', 'EQ-003', 'BM-003', 'CP-003',
          'RH-003', NOW(), NOW()
      );