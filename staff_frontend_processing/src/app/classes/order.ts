interface Order{
id:string;
food: Food[];
table:Table;
total:number;
orderStatus:string;
orderCreated:Date;
customer:Customer;
staff:Staff;
}
/*
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private String order_id;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "FOOD_ORDER_MAPPING", joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    private List<Food> items;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private Table table;
    @Column(nullable = false)
    private Double total;
    @Column(columnDefinition = "integer default 0")
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private LocalDateTime orderCreated;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(nullable = true, updatable = true, name = "staff_id")
    private Staff assignedStaff;
*/