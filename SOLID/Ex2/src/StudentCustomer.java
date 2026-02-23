public class StudentCustomer extends Customer {
  private final TaxRules taxRules;
  private final DiscountRules discountRules;

  public StudentCustomer() {
    this.taxRules = new StudentTaxes();
    this.discountRules = new StudentDiscount();
  }

  public TaxRules getTaxRules() {
    return this.taxRules;
  }

  public DiscountRules getDiscountRules() {
    return this.discountRules;
  }
}
