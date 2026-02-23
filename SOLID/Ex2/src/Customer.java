public class Customer {
  private final TaxRules taxRules;
  private final DiscountRules discountRules;

  public Customer() {
    this.taxRules = new TaxRules();
    this.discountRules = new DiscountRules();
  }

  public Customer(TaxRules taxRules, DiscountRules discountRules) {
    this.taxRules = taxRules;
    this.discountRules = discountRules;
  }

  public TaxRules getTaxRules() {
    return this.taxRules;
  }

  public DiscountRules getDiscountRules() {
    return this.discountRules;
  }
}
