use Math::Random::OO::Normal;
print("Hi!!! This is Perl \n");

#This generator returns decimals...

$firstGenerator = Math::Random::OO::Normal -> new(5);
$firstValue = $firstGenerator -> next();
print("Value is $firstValue");
