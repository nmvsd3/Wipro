Hibernate provides multiple ways to query

 from employee e where eid = :e 
1. HQL (Hibernate Query Language ) ---> Hibernate's own  a object- oriented version of SQL.
2. Native SQL - > Plain jdbc or database SQL (the way we did in core java (Jdbc))
3. Criteria API ->  which is the advanced one , actually this is not a part of  Hibernate , It's a part of JPA
( Jakarta Persistence API) specification.
  a)  the criteria api is a standardized across any JPA Provider (Hibernate , OpenJPA, )
  b)  We write the queries using Java Objects ( CriteriaBuilder , CriteriaQuery) instead of a normal String
  c) YOur code becomes type safe and easier to build dynamically.
  
So Hibernate is implements it  but it's a JPA feature

Real scenarios where we can use Criteria API 
1) For search filter page
2) combination of filtering the records like (name + email + date range)  ( Complex queries)
3) no string concatenation , we can implement using predicate

How we implement:
1)  criteria api -- Which is a JPA mechanism
2) criteriabuilder  -- Factory for building queries , conditions and expressions
3) Best for  : dynamic filters , complex queries and reusable DAO Layers



  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  